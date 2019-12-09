package shipping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Carrier {

    // it will be an identifier
    private final String registrationNumber;

    private final String name, country;

}
