SELECT latitude::DOUBLE PRECISION,longitude::DOUBLE PRECISION FROM environmental.temp_vector
WHERE temp_value BETWEEN ? AND ?
AND
latitude IN
(
	SELECT latitude FROM environmental.soils
	WHERE
	phaq BETWEEN ? AND ?
	AND
	sdra_descr = ?
 	AND
	latitude IN
	(
		SELECT latitude FROM environmental.rainfall_vector
		WHERE
		rainfall_amount BETWEEN ? AND ?
	)
)
AND
longitude IN
(
	SELECT longitude FROM environmental.soils
	WHERE
	phaq BETWEEN ? AND ?
	AND
	sdra_descr = ?
 	AND
	longitude IN
	(
		SELECT longitude FROM environmental.rainfall_vector
		WHERE
		rainfall_amount BETWEEN ? AND ?
	)
);

