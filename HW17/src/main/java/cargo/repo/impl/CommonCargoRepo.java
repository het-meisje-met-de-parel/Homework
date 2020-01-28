package cargo.repo.impl;


import static cargo.domain.CargoField.NAME;
import static cargo.domain.CargoField.WEIGHT;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import cargo.domain.Cargo;
import cargo.domain.CargoField;
import cargo.repo.CargoRepo;
import cargo.search.CargoSearchCondition;

public abstract class CommonCargoRepo implements CargoRepo {

    private static final List<CargoField> FIELDS_ORDER_TO_SORT_CARGOS = Arrays.asList(NAME, WEIGHT);

    private static final Comparator<Cargo> CARGO_NAME_COMPARATOR = Comparator.comparing(c -> String.valueOf (c.getName ()));

    private static final Comparator<Cargo> CARGO_WEIGHT_COMPARATOR = Comparator.comparing(Cargo::getWeight); // weight can't null

    protected Comparator<Cargo> createCargoComparator(CargoSearchCondition searchCondition) {
        Comparator<Cargo> result = null;
        //NAME, WEIGHT
        for (CargoField cargoField : FIELDS_ORDER_TO_SORT_CARGOS) {
            if (searchCondition.shouldSortByField(cargoField)) {

                if (result == null) {
                    result = getComparatorForCargoField(cargoField);
                } else {
                    result = result.thenComparing(getComparatorForCargoField(cargoField));
                }
            }
        }


        return result;
    }


    private Comparator<Cargo> getComparatorForCargoField(CargoField cargoField) {
        switch (cargoField) {

            case NAME: {
                return CARGO_NAME_COMPARATOR;
            }
            case WEIGHT: {
                return CARGO_WEIGHT_COMPARATOR;
            }
        }

        return null;
    }


}
