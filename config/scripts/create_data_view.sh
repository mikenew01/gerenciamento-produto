#!/bin/bash
until $(curl --output /dev/null --silent --head --fail http://kibana:5601); do
  echo "Aguardando o Kibana iniciar..."
  sleep 5
done

curl -X POST "http://kibana:5601/api/data_views/data_view" \
-H "Content-Type: application/json" \
-H "kbn-xsrf: true" \
-d '{
  "data_view": {
    "title": "logs-api-produto*",
    "timeFieldName": "@timestamp"
  }
}'
