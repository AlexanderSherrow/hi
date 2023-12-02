SELECT date_format(b.sell_date, "%Y %M" ) as sell_date,
	COUNT(b.vin) as total_vehicles_sold,
    ((1.1 * IFNULL(cost.total_part_cost,0)) + (SUM(s.purchase_price)*1.25)) as total_sales_income,
	(((1.1 * IFNULL(cost.total_part_cost,0)) + (SUM(s.purchase_price)*1.25)) - SUM(s.purchase_price)) as net_income
FROM Bought as b
LEFT JOIN Sold as s ON b.vin = s.vin
LEFT JOIN (
	SELECT  SUM(cost_per_part * part_quantity) as total_part_cost, po.vin 
    FROM PartsOrder as po
	LEFT JOIN Part as p ON po.purchase_order_number = p.purchase_order_number
    LEFT JOIN Sold as s1 ON po.vin = s1.vin
	GROUP BY po.vin
) as cost ON b.vin = cost.vin
GROUP BY date_format(b.sell_date, "%Y %M")
ORDER BY b.sell_date DESC