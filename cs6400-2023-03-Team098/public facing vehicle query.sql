select v.vin, v.model_name, v.model_year, v.description, v.chassis_type, v.manufacturer, v.fuel_type, v.mileage, (IFNULL(cost_of_parts, 0) + s.purchase_price * 1.25) as SalesPrice, s.purchase_date, group_concat(c.color) as car_color
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
    ((v.manufacturer like '%' and v.model_year like '%' and v.fuel_type like '%' and c.color like '%')
    or (v.description like '%' or v.model_year like '%' or v.model_name like '%' or v.manufacturer like '%'))
    and v.vin not in (select vin from PartsOrder natural join (select * from part where part.current_status = 'received' or part.current_status = 'ordered') as unistalled)
group by vin
ORDER BY vin asc
