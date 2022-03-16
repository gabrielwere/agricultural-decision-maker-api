WITH cte_rainfall_latitude AS(
	SELECT
	TRUNC(ST_Y(ST_CENTROID(rainfall_vector.geom))::NUMERIC,1) AS lat
	FROM rainfall_vector
	WHERE
	rainfall_amount BETWEEN 1000 AND 1200
),
cte_rainfall_longitude AS(
	SELECT
	TRUNC(ST_X(ST_CENTROID(rainfall_vector.geom))::NUMERIC,1) AS long
	FROM rainfall_vector
	WHERE
	rainfall_amount BETWEEN 1000 AND 1200
),
cte_soil_latitude AS(
	SELECT
	TRUNC(ST_Y(ST_CENTROID(soils.geom))::NUMERIC,1)
	FROM soils
	INNER JOIN cte_rainfall_latitude
	ON
	cte_rainfall_latitude.lat = TRUNC(ST_Y(ST_CENTROID(soils.geom))::NUMERIC,1)
	WHERE
	drai_descr = 'well drained'
	AND
	sdra_descr = 'well'
	AND
	rdep_descr = 'deep'
),
cte_soil_longitude AS(
	SELECT
	TRUNC(ST_X(ST_CENTROID(soils.geom))::NUMERIC,1)
	FROM soils
	INNER JOIN cte_rainfall_longitude
	ON
	cte_rainfall_longitude.long = TRUNC(ST_X(ST_CENTROID(soils.geom))::NUMERIC,1)
	WHERE
	drai_descr = 'well drained'
	AND
	sdra_descr = 'well'
	AND
	rdep_descr = 'deep'
	
)

SELECT 
ST_Y(ST_CENTROID(temp_vector.geom)) AS lat,
ST_X(ST_CENTROID(temp_vector.geom)) AS long

FROM 
temp_vector
WHERE 
temp_value BETWEEN 16 AND 25
AND
TRUNC(ST_Y(ST_CENTROID(temp_vector.geom))::NUMERIC,1) IN (
	SELECT * FROM cte_soil_latitude
)
AND
TRUNC(ST_X(ST_CENTROID(temp_vector.geom))::NUMERIC,1) IN (
	SELECT * FROM cte_soil_longitude
);