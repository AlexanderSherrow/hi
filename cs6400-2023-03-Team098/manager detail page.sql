SELECT v.vin, 
	v.chassis_type,
    v.model_year,
    v.model_name,
    v.manufacturer, 
    v.fuel_type, 
    c.color, 
    v.mileage, 
    v.description, 
	((1.25 * s.purchase_price) + (1.1 * IFNULL(cost.total_part_cost,0))) as sales_price, 
    s.purchase_price as original_purchase_price, 
    IFNULL(cost.total_part_cost,0) as total_part_cost, 
    s.purchase_date,
    IFNULL(seller_business.first_name, seller_individual.first_name) as seller_first_name,
    IFNULL(seller_business.last_name, seller_individual.last_name) as seller_last_name,
    IFNULL(seller_business.contact_title, "N/A") as seller_contact_title,
    IFNULL(seller_business.business_name, "N/A") as seller_business_name,
    seller.email, 
    seller.phone_number,
    CONCAT(seller.street, "," , seller.city, "," , seller.state, "," , seller.postal_code) as seller_address,
	clerks.first_name as clerk_first_name, 
    clerks.last_name as clerk_last_name,
    IFNULL(b.sell_date, "N/A") as sales_date,
    IFNULL(sales.first_name, "N/A") as sales_person_first_name,
    IFNULL(sales.last_name, "N/A") as sales_person_last_name,
    IFNULL(buyer.email, "N/A") as buyer_email,
    IFNULL(buyer.phone_number, "N/A") as buyer_phone_number,
    IFNULL(CONCAT(buyer.street, "," , buyer.city, "," , buyer.state, "," , buyer.postal_code), "N/A") as buyer_address,
	IFNULL(IFNULL(buyer_business.first_name, buyer_individual.first_name), "N/A") as buyer_first_name,
    IFNULL(IFNULL(buyer_business.last_name, buyer_individual.last_name), "N/A") as buyer_last_name,
    IFNULL(buyer_business.contact_title, "N/A") as buyer_contact_title,
    IFNULL(buyer_business.business_name, "N/A") as buyer_business_name
FROM Vehicle as v
LEFT JOIN (
	SELECT v1.vin, group_concat(c1.color) as color
	FROM Vehicle as v1
    LEFT JOIN Color as c1 ON v1.vin = c1.vin
    GROUP BY v1.vin
) as c ON v.vin = c.vin
LEFT JOIN Sold as s ON v.vin = s.vin
LEFT JOIN (
	SELECT  SUM(cost_per_part * part_quantity) as total_part_cost, po.vin 
    FROM PartsOrder as po
	LEFT JOIN Part as p ON po.purchase_order_number = p.purchase_order_number
    LEFT JOIN Sold as s1 ON po.vin = s1.vin
	GROUP BY po.vin
) as cost ON v.vin = cost.vin
LEFT JOIN Customer as seller ON IFNULL(s.business_tax_id, s.drivers_license_number) = IFNULL(seller.business_tax_id, seller.drivers_license_number)
LEFT JOIN Business as seller_business ON seller.business_tax_id = seller_business.business_tax_id
LEFT JOIN Individual as seller_individual ON seller.drivers_license_number = seller_individual.drivers_license_number
LEFT JOIN User as clerks ON s.inventoryClerk = clerks.username
LEFT JOIN Bought as b ON v.vin = b.vin
LEFT JOIN User as sales ON b.salespeople = sales.username
LEFT JOIN Customer as buyer ON IFNULL(b.business_tax_id, b.drivers_license_number) = IFNULL(buyer.business_tax_id, buyer.drivers_license_number)
LEFT JOIN Business as buyer_business ON buyer.business_tax_id = buyer_business.business_tax_id
LEFT JOIN Individual as buyer_individual ON buyer.drivers_license_number = buyer_individual.drivers_license_number
GROUP BY v.vin
ORDER BY v.vin ASC