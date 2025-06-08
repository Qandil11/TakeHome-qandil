# Tech Test

You have been provided with an in-development Android app. The application uses an API to display lists of data about characters from the show "Game of Thrones". The project has some bugs and notable UI mismatches compared to the given designs.

## Some issues that have been reported

- App crashes on launch
- Major discrepancies with the designs e.g. white padding

## Improvements required

- A new feature needs to be added that would allow a user to search the list by character name.

## Resources

The API endpoint is available from:
[https://yj8ke8qonl.execute-api.eu-west-1.amazonaws.com/characters](https://yj8ke8qonl.execute-api.eu-west-1.amazonaws.com/characters)
Requests to that endpoint will require the following header:
"Authorization": "Bearer 754t!si@glcE2qmOFEcN"

Designs: 

![img_design_1.png](app%2Fsrc%2Fmain%2Fres%2Fdrawable%2Fimg_design_1.png) ![img_design_2.png](app%2Fsrc%2Fmain%2Fres%2Fdrawable%2Fimg_design_2.png)

## Criteria on which we will assess your submission

- Closeness to designs (pragmatism is encouraged and pixel perfection is NOT required)
- Code quality, included but not limited to, design patterns and organisation of the application code
- Scalability
- Error handling
- Unit tests

## Submission details

We would like you to fix the app's user facing issues (both documented and undocumented), add the additional search feature and submit the codebase in a more scalable state.

Please use version control. Import the supplied code as is to git and commit your changes through that. This will allow us to review the changes you have made.

We expect you should spend no more than 3 hours on this work. We appreciate you taking the time to work on this and understand that sometimes it's not possible to spend as much time as you would like. If there are any aspects of the codebase you would have liked to work on with more time, please detail these in the ReadME file to give us some insight in to your process.

## Fixes

- **Crash on Launch**  
  Fixed a crash caused by a mismatch between the API model and the `ApiCharacter` data class. The `aliases` field was incorrectly defined as `List<Int>` instead of `List<String>`.

- **UI Discrepancies**  
  Removed unnecessary white padding and updated colors, text styles, and layout spacings to align more closely with the design mockups.

---

## New Features

- **Search Bar**  
  Implemented an `OutlinedTextField` that allows users to filter characters by name.

- **Loading Spinner**  
  Added a visual loading indicator when data is being fetched from the API.

- **Error Handling**  
  Displayed a user-friendly message when data fails to load (e.g., due to network issues).

- **Top App Bar**  
  Included a centered title bar to improve visual hierarchy and app structure.

- **Exit Confirmation Dialog**  
  Introduced a dialog that prompts users to confirm before closing the app using the back button.

---

## Code Quality Improvements

- **MVVM Architecture**  
  Refactored code into `ViewModel`, `Repository`, and `Service` layers to improve scalability and separation of concerns.

- **Reactive UI with StateFlow**  
  Used Kotlin `StateFlow` to observe and react to changes in loading, data, and error states.

- **Modularization**  
  Code is now structured into logical, testable components with clearly defined responsibilities.

---

## Unit Tests

- `CharacterRepositoryTest`  
  Validates that `fetchCharacters()` correctly returns data when the API responds successfully. Uses `mockk` and `runTest`.

- `CharacterSearchTest`  
  Verifies character search logic, covering:
    - Partial and exact name matches
    - Case-insensitive search
    - Handling of no-match scenarios
    - Multiple matches for a query

---

## What I Would Improve with More Time

- Add UI tests using Espresso or Jetpack Compose Testing.
- Use `MockWebServer` to simulate API responses for end-to-end tests.
- Implement paging to efficiently handle larger datasets.

---