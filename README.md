# AGRICULTURAL DECISION MAKER API

## What it is

An API that returns locations that are suitable to grow a particular crop.<br>

The API has four endpoints:

1. #### `\RAINFALL`
This endpoint returns all the latitude,longitude points that meet the rainfall specifications
of a particular crop.<br>
A sample database query would appear as follows:<br>
`SELECT latitude,longitude FROM environmental.rainfall WHERE`<br> `rainfall_amount BETWEEN 600 AND 1000`

2. #### `\SOIL`
This endpoint returns all the latitude,longitude points that meet the soil specifications of a
particular crop.<br>
A sample database query would appear as follows:<br>
`SELECT latitude,longitude FROM environmental.soil WHERE`<br>
`surface_drainage_description = ‘well’`<br>
`AND`<br>
`drainage_descr = ‘well drained’`<br>
`AND`<br>
`rootable_depth_description = ‘deep’`<br>
`AND`<br>
`phaq BETWEEN 5.5 AND 7`

3. #### `\TEMPERATURE`
This endpoint returns all the latitude,longitude points that meet the temperature specifications of a particular crop.<br>
A sample database query would appear as follows:<br>
`SELECT latitude,longitude FROM environmental.temperature WHERE
temperature_value BETWEEN 17.5 AND 30`

4. #### `\AGGREGATION`
This endpoint returns all the latitude,longitude points that meet the rainfall,soil and temperature specifications of a particular crop.<br>
This is the endpoint that is utilised by the [GIS UI](https://github.com/gabrielwere/gis-ui).<br>
In this repository,checkout the  `gis_api/src/main/java/com/dao/aggregation.sql` to see the structure of the SQL query.

## How it works

The `environmental` and `non-environmental` folders in this repo hold some GIS files.

1. ##### `ENVIRONMENTAL`
This contains rainfall,soil and temperature data(specific to Kenya).

2.  ##### `NON ENVIRONMENTAL`
This contains data on the major roads,airfields and urban towns and areas in Kenya.

The GIS files are stored in a `POSTGRES` spatial database,which is divided into two schemas,namely `environmental` and `non-environmental`.<br>
This is why,in the sample queries above,you see the tables being quoted as `environmnetal.temperature` ; because of the schemas.

The best locations are found using `Multicriteria Decision Making`.<br> Check out [this](https://www.youtube.com/watch?v=J9zwPqLkH1w&list=PLFsDe5VTL0GONrBajWDmh6J2MdzrW0UwM) link to learn about it.

## Technologies
 
 * Java
 * POSTGRESQL

Check out `gis_api/pom.xml` to see dependencies required.

## Data sources

1. [Soil data](https://data.isric.org/geonetwork/srv/api/records/73e27136-9efe-49e4-af35-fd98b841d467).
2. [Rest of the data](https://www.wri.org/data/kenya-gis-data).

## Additional Information

Check out the [GIS UI](https://github.com/gabrielwere/gis-ui) and the [Knowledge Base Connector](https://github.com/gabrielwere/knowledge-base-connector) repositories to see this API in use.<br>

