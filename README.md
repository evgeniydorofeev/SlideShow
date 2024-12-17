# Slideshow

To get started:

1. Run `mvnw clean package` to build an application image.
2. Run `docker-compose up -d` in the project root folder to run the application.

The app is listening on port 8080. You can test the API by sending the following example requests:

1. curl -X POST http://localhost:8080/addImage -H "Content-Type: application/json" -d "{\"id\": null, \"name\":\"image\", \"url\": \"url1\", \"duration\": 1}" 
3. curl -X DELETE http://localhost:8080/deleteImage/1
2. curl -X POST http://localhost:8080/addSlideshow -H "Content-Type: application/json" -d "{\"id\": null, \"name\":\"slideshow\", \"imageIds\": [1]}"
4. curl -X DELETE http://localhost:8080/deleteSlideshow/1
5. curl -X GET http://localhost:8080/images/search?duration=1&keyword=url1
6. curl -X GET http://localhost:8080/slideShow/1/slideshowOrder
7. curl -X GET http://localhost:8080/slideShow/1/proof-of-play/1

addImage and addSlideShow operations return the created entity ID which can be used in subsequent requests.

In this version Hibernate is configured to re-create DB schema after each restart, so all the data created in the previous session is lost.

To run integration test: `mvnw failsafe:integration-test`



