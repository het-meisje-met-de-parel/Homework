package application.db;


public class DBQueries {
    
    public static final String SELECT_ALL_CARGO = "SELECT * FROM `cargo`";
    public static final String SELECT_CARGO_BY_ID = "SELECT * FROM `cargo` WHERE `id` = ?";
    public static final String SELECT_CARGOS_BY_NAME = "SELECT * FROM `cargo` WHERE `name` = ?";
    public static final String SELECT_CARGOS_WITH_ORDER = "SELECT * FROM `cargo` ORDER BY %s %s";
    public static final String COUNT_CARGOS = "SELECT COUNT(*) FROM `cargo`";
    public static final String DELETE_ALL_CARGO = "DELETE FROM `cargo`";
    public static final String DELETE_CARGO_BY_ID = "DELETE FROM `cargo` WHERE `id` = ?";
    public static final String INSERT_CARGO = 
        "INSERT `cargo` ( "
        + "  `id`, `name`, `weight`, `cargo_type`, "
        + "  `size`, `material`, `expiration_date`, "
        + "  `store_temperature`"
        + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String UPDATE_CARGO = 
        "UPDATE `cargo` SET "
        + "  `name` = ?, "
        + "  `weight` = ?, "
        + "  `cargo_type` = ?, "
        + "  `size` = ?, "
        + "  `material` = ?, "
        + "  `expiration_date` = ?, "
        + "  `store_temperature` = ? "
        + "WHERE `id` = ?";
    
}
