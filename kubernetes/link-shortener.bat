call kubectl delete deploy link-shortener
call mvn -f ../link-shortener clean install -DskipTests
call docker build -t link-shortener ../link-shortener
call kubectl apply -f link-shortener-deploy.yaml