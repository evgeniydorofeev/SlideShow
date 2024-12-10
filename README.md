# Slideshow

To get started:

1. Run mvnw clean package to build application image
2. Run docker-compose up in the project root folder

The app is listening on port 8080. You can test the API by sending the following requests:

1. curl -X POST http://localhost:8080/addImage  -H "Content-Type: application/json" -d "{\"id\": null, \"name\":\"image1\", \"url\": \"url1\", \"duration\": 1}" 
3. curl -X DELETE http://localhost:8080/deleteImage/1
2. curl -X POST http://localhost:8080/addSlideShow  -H "Content-Type: application/json" -d "{\"id\": null, \"name\":\"image1\", \"url\": \"url1\", \"duration\": 1}"
4. curl -X DELETE http://localhost:8080/deleteSlideshow/{id}
5. curl -X GET http://localhost:8080/images/search?duration=1
6. curl -X GET http://localhost:8080/slideShow/{id}/slideshowOrder
7. curl -X GET http://localhost:8080/slideShow/{id}/proof-of-play/{imageId}
