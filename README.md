# How to

## Installation

TODO add more detailed information. 
In order to run Application a new image must be created which is used/referenced
in docker-compose file. To ease the process a
[docker-maven-plugin](https://github.com/spotify/docker-maven-plugin)from Spotify has been added as a dependency.
So using the following commands application can be run on docker containers:

```bash
#Run MySQL container for tests
docker run -d -p 3306:3306 --name garbags-mysql -e MYSQL_ROOT_PASSWORD=23secretpass98p -e MYSQL_DATABASE=garbagsservice mysql:5.7.22
#Build the application, runs tests and creates an image
mvn clean package docker:build 
#The above command will create a new image every time it's executed, so it's necessary to perform following command in order to get rid of dangling images(also removes stopped containers, networks, etc.)
docker system prune # Optional
#Stop MySql container, this is necessary before running application since the same ports have been used during docker compose
docker stop garbags-mysql
# And finally to start the app(including db):
docker-compose up -d # Note that it may take some time for the application to start, run without -d flag to see the progress
# To stop the app:
docker-compose down
```

## Usage

## Contributing

## License
