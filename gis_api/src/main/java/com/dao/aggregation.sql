WITH cte_rainfall_latitude AS(
	SELECT
	TRUNC(ST_Y(ST_CENTROID(rainfall_vector.geom))::NUMERIC,1) AS lat
	FROM environmental.rainfall_vector
	WHERE
	rainfall_amount BETWEEN ? AND ?
),
cte_rainfall_longitude AS(
	SELECT
	TRUNC(ST_X(ST_CENTROID(rainfall_vector.geom))::NUMERIC,1) AS long
	FROM environmental.rainfall_vector
	WHERE
	rainfall_amount BETWEEN ? AND ?
),
cte_soil_latitude AS(
	SELECT
	TRUNC(ST_Y(ST_CENTROID(soils.geom))::NUMERIC,1)
	FROM environmental.soils
	INNER JOIN cte_rainfall_latitude
	ON
	cte_rainfall_latitude.lat = TRUNC(ST_Y(ST_CENTROID(soils.geom))::NUMERIC,1)
	WHERE
	drai_descr = ?
	AND
	sdra_descr = ?
	AND
	rdep_descr = ?
	AND
	phaq BETWEEN ? AND ?
),
cte_soil_longitude AS(
	SELECT
	TRUNC(ST_X(ST_CENTROID(soils.geom))::NUMERIC,1)
	FROM environmental.soils
	INNER JOIN cte_rainfall_longitude
	ON
	cte_rainfall_longitude.long = TRUNC(ST_X(ST_CENTROID(soils.geom))::NUMERIC,1)
	WHERE
	drai_descr = ?
	AND
	sdra_descr = ?
	AND
	rdep_descr = ?
	AND
	phaq BETWEEN ? AND ?
	
)

SELECT 
ST_Y(ST_CENTROID(temp_vector.geom)) AS lat,
ST_X(ST_CENTROID(temp_vector.geom)) AS long

FROM 
environmental.temp_vector
WHERE 
temp_value BETWEEN ? AND ?
AND
TRUNC(ST_Y(ST_CENTROID(temp_vector.geom))::NUMERIC,1) IN (
	SELECT * FROM cte_soil_latitude
)
AND
TRUNC(ST_X(ST_CENTROID(temp_vector.geom))::NUMERIC,1) IN (
	SELECT * FROM cte_soil_longitude
);