# Slideshow

To get started:

1. Run mvnw clean package to build application image
2. Run docker-compose up in the project root folder

The app is listening on port 8080. You can test the API by sending the following requests:

1. curl -X POST http://localhost:8080/addImage -H "Content-Type: application/json" -d "{\"id\": null, \"name\":\"image\", \"url\": \"url1\", \"duration\": 1}" 
3. curl -X DELETE http://localhost:8080/deleteImage/1
2. curl -X POST http://localhost:8080/addSlideshow -H "Content-Type: application/json" -d "{\"id\": null, \"name\":\"slideshow\", \"imageIds\": [1]}"
4. curl -X DELETE http://localhost:8080/deleteSlideshow/1
5. curl -X GET http://localhost:8080/images/search?duration=1&keyword=url1
6. curl -X GET http://localhost:8080/slideShow/1/slideshowOrder
7. curl -X GET http://localhost:8080/slideShow/1/proof-of-play/1

Note that addImage and addSlideShow return created the entity Id which can be used in requests using ID.
