SELECT PartsOrder.purchase_order_number,
SUM(part.cost_per_part * part.part_quantity)
FROM PartsOrder natural join part
GROUP BY PartsOrder.purchase_order_number
