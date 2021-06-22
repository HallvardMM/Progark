# Oving 2
## Author: Snorre og Hallvard

### Step 4
### a)

Architectural patterns:
- Entity Component System
- Model View
- Pipe and filter

Design patterns:
- Observer
- State
- Template Method
- Abstract Factory

What are the relationships and differences of architectural patterns and design
patterns?
- Differences are that architectural patterns are used to generalize the structural organization for software systems, while design patterns are used to solve a localized problem.
- Relationships or similarities are that both are used to solve problems and have become standard solutions to common problems. These patterns allows us to deconstruct a large complex structure and build using simple parts.

### b)
Singelton:
- We applied the Singelton pattern to the class Ball. We made the instanciation private and made a getInstance function that returns the ball if it exists or creates and returns the ball if it doesn't exists.

Observer:
- We applied the Observer pattern to the classes Ball, Player and PlayState. Ball is observed and notifys the observers Ball and Player when it reaches one of the boarders. We also made a BallObserver interface which Player and Playstate implements.

### c)
Singelton advantages:
- Useful when excatly one instance of the object is needed.
- Reassurs that only one instance is created which can remove bugs and make the code easier to understand.
- Advantage in this program is that we only need one ball.

Singelton disadvantages:
- Can create problems when using threading. If two threads try to create a singelton at the same time.
- Creates a global state in the class.
- Can often be used when it is not needed and adds unnecessary complexity.

Observer advantages:
- Makes it easy to know which classes are dependent on the observed class.
- Is a standard pattern which makes the code easy to understand.
- Allows for one-to-many dependency between the objects in the app and makes it easy to add or remove observers.
- makes the coupling between objects controlled.

Observer disadvantages:
- The observer can add complexity and performance issues if the handling of observers are implemented poorly.
- It adds a possibility of memory leak.
