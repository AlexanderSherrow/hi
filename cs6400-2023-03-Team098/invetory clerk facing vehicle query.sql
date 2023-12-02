select  vin, chassis_type, model_year, model_name, description, manufacturer, fuel_type, mileage, cost_of_parts, purchase_price, car_color
from
(select v.vin, c.driver_license, c.business_tax_id, v.chassis_type, v.model_year, v.model_name, v.description, v.manufacturer, v.fuel_type, v.mileage, IFNULL(cost_of_parts, 0) as cost_of_parts, s.purchase_price, group_concat(c.color) as car_color
 from 
 ((vehicle v natural join color c natural join sold s natural join customer cu natural join individual natura) left join 
(SELECT
    vin, SUM(total_cost_per_order) * 1.10 AS cost_of_parts
FROM (
    SELECT
        PartsOrder.vin,
        SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order
    FROM
        PartsOrder
    JOIN
        part ON PartsOrder.purchase_order_number = part.purchase_order_number
    GROUP BY
        PartsOrder.purchase_order_number, PartsOrder.vin
) AS costs_per_order
GROUP BY
    vin) as parts on v.vin = parts.vin)
GROUP BY vin
) as vehicles