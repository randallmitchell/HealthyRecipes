This code is under construction and doesn't fully reflect my thoughts on design and architecture. When done it should be a solid general purpose application codebase that can be modified to work for most small to medium size business domains that have use for an Android application. The architecture is generic and focuses on predictability, maliability, and extensability. Really, the architecture should go pretty far (once done) and is largely proven in the wild under a very small sample set. Things have been reevaluated and adjusted over time - generally with good results.

For now the codebase is handling a larger set of topics but handling them each in a more trivial nature. Essentially, the architecture is closing towards robust but is not exercised in a robust way through nuanced real world requirements. In abstract but simple terms, the boxes are there but don't have much in them.

# Things that need more immediate attention
- The UI/UX is missing essential experience mechanics (e.g. a title bar). This will take a while to get to.
- The packages structure needs It's first pass on organization.
  - The higher level packages should be converted into build modules in order to protect the various parts of the code from eachother.
  - The root dependency is the `usecase` package/module so it should not depend on other modules. This is where the business rules live and where _the important_ testing goes.
  - `Services` should not depend on other `services`. (predictibility/mutability/extensibility)
 
# General concepts
- Architecture/design is based on primary concepts that should drive decision making within the code:
  - `predictibility` : how easy is it know exactly what the impact of changes are?
  - `mutability` : how easy is it to make changes large and small?
  - `extensibility` : how easy is it to add to as the domain grows?
  - `accessibility` : how easy is it for developers to understand?
- Module dependency hierarchy: `view` -> `viewmodel` -> `usecase` <- `service`.
- Arhitecture/design attempts to support some SOLID programming principles
  - Single Responsibility : who is going to request a change?
    - ui rules are controlled by the design owner. those are isolated to the `view` layer.
    - ux rules are controlled by the user experience owner. those are isolated to the `viewmodel` layer.
    - domain rules are controlled by the product owner. those are isolated and protected in the `usecase` layer.
    - individual remote services are controlled by the system architect. each remote service's mechanics are wrapped and isolated to their own `services`.
    - local databases and other similar local functions are controlled by the application architect. those are isolated to their own `services`.
  - Interface Segregation Principle
    - `Usecases` favor this over `viewmodels` talking directly to `repositories`.
  - Dependency Inversion
    - The `service` layer should depend on the `usecase` layer so as to protect the `usecase`'s enclosed business rules. In that way the `usecase` layer should define all `service` interfaces.
- `Usecase` tests validate business rules.
- `Viewmodel` tests validates UI/UX rules.
- `Services` and `views` should not make decisions or interpret information.
- `Services` transmit data using DTOs that match their backends. Those DTOs should be mapped within the `service` into app `usecase` API modeling.
- The navigation system uses a mechanic called "flows" that implement a user experience concept.
  - _i've found them more agreeable than some other more generic [and subsequently more disjointed] navigation schemes I've worked with in the past. Aligning with product conceptually is quite often a boon._
- This codebase borrows the concept of `value objects` from Domain Driven Design. It's a good way to validate data as it enters the system without additional validations as the data moves about.

# Big ticket functional tech items for the near future
- ~~error handling~~
- api support
- analaytics service
- see [issues](https://github.com/randallmitchell/HealthyRecipes/issues) for more
- i'm planning on building out the wiki in this GitHub project with architecture and design documentation along with some related educational direction.
- authentication and authorization

# Build Notes

## Seed Data
Create a json file at `/app/src/main/res/raw/local_recipes.json` using the format
```
{
  "recipes": [
    {
      "id": "1",
      "description": "zesty chickpea salad",
      "servings": "1 serving | make 4",
      "instructions": "15 min @ 350 degrees",
      "ingredients": [
        {
          "units": "1.5",
          "unitType": "tablespoon",
          "name":  "olive oil"
        }
      ],
    }
  ]
}
```
This was not added to source control so as
to protect intellectual property.

## New Relic Keys

Create the file: `gradle/remote_services_configs.properties` with content
```
NEW_RELIC_API_KEY="{value}"
```

# Coming Soon
APIs
