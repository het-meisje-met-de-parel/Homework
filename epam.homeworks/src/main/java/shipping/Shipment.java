package shipping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Shipment {

    // it will be an identifier
    private final String trackingNumber;

    private final Cargo cargo;

    private final Carrier carrier;

}
