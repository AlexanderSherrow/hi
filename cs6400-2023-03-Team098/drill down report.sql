SELECT u.first_name, u.last_name, COUNT(b.vin) as total_cars_sold, SUM(((1.25 * s.purchase_price) + (1.1 * IFNULL(cost.total_part_cost,0)))) as total_sales
FROM Bought as b
LEFT JOIN Sold as s ON b.vin = s.vin
LEFT JOIN (
	SELECT  SUM(cost_per_part * part_quantity) as total_part_cost, po.vin 
    FROM PartsOrder as po
	LEFT JOIN Part as p ON po.purchase_order_number = p.purchase_order_number
    LEFT JOIN Sold as s1 ON po.vin = s1.vin
	GROUP BY po.vin
) as cost ON b.vin = cost.vin
LEFT JOIN User as u ON b.salespeople = u.username
WHERE date_format(b.sell_date, "%Y %M" ) = "2023 August"
GROUP BY b.salespeople
ORDER BY total_cars_sold DESC, total_sales DESC