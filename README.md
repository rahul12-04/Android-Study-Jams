# Android-Study-Jams

Bookly -Library Book Management App

<b> Problem Statement: </b>

University libraries have a very vast collection of books from which students issue books based on their requirements. Most of the colleges provide this facility. However, many times it has been observed that students miss the return date, especially when they issue a lot of books during end sem or maybe in group studies and end up paying fines. Few colleges including mine have an ERP system, but, for that, we need to login into ERP and then navigate to the library section. So everytime we need to login to check and also it has a timeout, after which we get logged out of ERP. Further it has been observed that many times there are long queues for issuing books as it requires scanning QR code/barcode on book as well as Identity card after which library staff will put entry in their own system and then issue the book. This process could be further optimized by automating things. Looking at all these issues, it seems that right now is the high time to develop some user-friendly mobile app that could help students effectively manage books and it could further be extended to make the book issuing process a little bit more efficient.

<b> Proposed Solution: </b>

This library book management app proposes a solution to most of the problems listed above. Bookly is a simple but yet useful library book management app for University students. Presently it addresses the concern of students by providing them an option to track the books which they issue from the library. It is equipped with the scanner which is capable of scanning a lot of formats of code which includes QR Code and Barcode (complete list can be accessed [here](https://github.com/yuriy-budiyev/code-scanner)), which is most frequently used. While developing this app, it has been assumed that there exists some API for fetching the book details from the university database and this API needs to be linked with the app through source code and network requests need to be implemented accordingly. Presently, the app uses a dummy book data list included with the app. Also the scanner comes with a flashlight button and autofocus button for clearly scanning code. So for using this app, students need to scan the code present on the book, after which it will create an entry of the book along with the issue date. Also a return button is provided, which needs to be clicked while returning the book, which removes the corresponding entry.

A detailed demo of using the Bookly app can be accessed by clicking [here](https://www.youtube.com/watch?v=CiOyOdtIntQ&feature=youtu.be).

This app could be further linked with University’s student database for implementing the login system at the beginning, post which it could even be used to issue book by scanning the book’s code after which it will send the some OTP to library’s computer which generates the QR code of OTP which user needs to scan to issue the book. So in this way, this app could be further extended to make the book issuing process even faster.

<b> Functionality & Concepts used : </b>

This app is built using Android Architecture components and best practices are taken care of to provide users with robust and reliable experience. App comes with a simple and easy to navigate user interface. Home screen contains two buttons, one for scanning the code and another for reviewing the issued book’s list. Few android concepts used in developing bookly are:

- Separation of Concerns: UI layer and Data layer are clearly separated by using various class objects. The UI layer consists of UI elements and state holders like ViewModel class. Data layer is implemented through a repository.

- Constraint Layout: Layouts for activity and all fragments are designed using constraint layout which ensures proper UI rendering for devices of different sizes.

- RecyclerView : RecyclerView is a viewGroup which contains the view corresponding to data binded to it. It provides an efficient way to display the list of books issued. RecyclerView dynamically creates the views that fit in the region and recycles the view once it gets out of the phone screen upon scrolling.

- Navigation Library: Bookly contains only single activity, rest all other views are implemented through fragments. For navigating between fragments, navigation library is used, which provides very useful navigation components like NavHostFragment, NavController and navGraph. These all components are used by Bookly for navigating between fragments.

- ViewModel: ViewModel is used to provide data between repository and view and also it survives configuration changes. It is used to exchange book data between repository and both Scanner view and Currently Issued view separately. Both the Views are initialized with their own ViewModel class object for exchanging data with repository.

- Room Database: Room library provides an abstraction layer built over SQLite which makes it very easy to directly map objects to raw database content, as well as easily define type-safe queries for interacting with data. We no longer need to generate the boilerplate code, as it can easily be achieved through the use of annotations provided by Room. Data of books issued by the user is stored using local Room database only.

- LiveData: LiveData is a life cycle aware component, which is used to observe any changes in the objects wrapped in its component. In other words, it is used to observe any change, like in our Bookly app, LiveData automatically updates when data in Room database changes, for example, if a user pressed the “Return” button, then the entry of that particular book in the database gets removed, and LiveData would automatically update the list.

<b> Application Link & Future Scope : </b>

Bookly can be accessed [here](https://drive.google.com/file/d/1rrTDy2tmPDKofVNA2Z1AYIl1kVKQ-NZ-/view?usp=sharing).

Presently it is in development mode. Users first need to connect or link the app with their university book database may be offline one or online one (implementation of any GET Requests library needs to be set up for online database). Once we get access to University’s student database, then, we further add a one time login system to the app. After authentication, we can implement a TWO WAY SCANNING method for issuing book, wherein the user needs to first scan book’s code at counter after that there would be OTP encoded as QR code rendered on screen at counter, which user needs to scan again, which finally marks book to be issued in the name of that user in library’s systems. Similar process could be employed for returning books. At first, it may look a bit complicated but, once implemented, then it would make the process of issuing and returning books much faster and more efficient.
