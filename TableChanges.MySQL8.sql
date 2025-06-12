-- ALTER TABLE vehicles DROP PRIMARY KEY;
-- ALTER TABLE vehicles ADD vehicle_id INT AUTO_INCREMENT PRIMARY KEY;
-- ALTER TABLE vehicles ADD UNIQUE (VIN);

-- ALTER TABLE inventory ADD vehicle_id INT;
-- ALTER TABLE sales_contracts ADD vehicle_id INT;
-- ALTER TABLE lease_contracts ADD vehicle_id INT;
-- SET SQL_SAFE_UPDATES = 0;

-- UPDATE inventory i
-- JOIN vehicles v ON i.vin = v.vin
-- SET i.vehicle_id = v.vehicle_id
-- WHERE i.vin = v.vin;

-- UPDATE sales_contracts s
-- JOIN vehicles v ON s.vin = v.vin
-- SET s.vehicle_id = v.vehicle_id
-- WHERE s.vin = v.vin;

-- UPDATE lease_contracts l
-- JOIN vehicles v ON l.vin = v.vin
-- SET l.vehicle_id = v.vehicle_id
-- WHERE l.vin = v.vin;

-- SET SQL_SAFE_UPDATES = 1;

