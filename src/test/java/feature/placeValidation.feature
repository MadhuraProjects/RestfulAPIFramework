Feature: Validating place APIs

Scenario Outline: Verify if the Place is being successfully added
	Given AddPlace Payload with "<name>" "<language>" "<address>"
	When user calls "AddPlaceAPI" with "Post" http request
	Then API call gets success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	And verify place_id created maps to "<name>" using "GetPlaceAPI"

Examples:
| name   | language | address           |
| AAHouse| English  | World Trade House |	
#| BBHouse | Spanish | Budge Budge |

Scenario: verify if Delete Place functionality is working

	Given DeletePlace payload
	When user calls "deletePlaceAPI" with "Post" http request
	Then API call gets success with status code 200
	And "status" in response body is "OK"
	