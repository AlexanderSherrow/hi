SELECT
    vendor_name,
    SUM(total_cost_per_order) AS total_cost_for_vendor
FROM (
    SELECT
        PartsOrder.vendor_name,
        SUM(part.cost_per_part * part.part_quantity) AS total_cost_per_order
    FROM
        PartsOrder
    JOIN
        part ON PartsOrder.purchase_order_number = part.purchase_order_number
    GROUP BY
        PartsOrder.purchase_order_number, PartsOrder.vendor_name
) AS costs_per_order
GROUP BY
    vendor_name;