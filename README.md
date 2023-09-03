# kognitos
* Resources Used-
AWS Lambda
AWS API Gateway
AWS Cloudwatch
AWS s3

* curl for API-

1. lowerCase -

curl --location --request POST 'https://fc3ndxyl2j.execute-api.us-west-2.amazonaws.com/dev/word' \
--header 'Content-Type: application/json' \
--data-raw '{
"word": "kognitos"
}'

Response-
{
"occurrences": 4
}


===============

2. upperCase-

curl --location --request POST 'https://fc3ndxyl2j.execute-api.us-west-2.amazonaws.com/dev/word' \
--header 'Content-Type: application/json' \
--data-raw '{
"word": "Kognitos"
}'

Response -
{
"occurrences": 5
}









