
select count(*) as carsNotForSale from(
select v.vin, v.model_name, v.model_year, v.description, v.chassis_type, v.manufacturer, v.fuel_type, v.mileage, (IFNULL(cost_of_parts, 0) + s.purchase_price * 1.25) as SalesPrice, s.purchase_date
 from 
 ((vehicle v natural join color c natural join sold s natural join customer cu) left join 
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
    where 
    v.vin not in (select vin from bought) and
	v.vin in (select vin from PartsOrder natural join (select part.purchase_order_number from part where part.current_status = 'received' or part.current_status = 'ordered') as unistalled)
GROUP BY vin
ORDER BY vin asc
) as vehiclesForSale