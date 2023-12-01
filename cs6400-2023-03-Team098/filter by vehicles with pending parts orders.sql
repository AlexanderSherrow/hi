select vin, chassis_type, model_year, manufacturer, model_name, fuel_type, mileage, (cost_of_parts + (purchase_price * 1.25)) as sales_price
 from 
 vehicle natural join color natural join sold natural join 
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
    vin) as cost_of_parts
