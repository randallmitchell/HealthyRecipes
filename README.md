This code is under construction and doesn't fully reflect my thoughts on design and architecture. When done it should be a solid general purpose application codebase that can be modified to work for most small to medium size business domains that have use for an Android application. Really, the architecture should go pretty far and similar enough codebases to this (once done) have been proven in the wild for years under a very small sample set. Things have been reevaluated and adjusted over time - generally with good results.

# Things that need more immediate attention
- the UI/UX is missing some essential experience mechanics (e.g. a title bar). this will take a while to get to.
- the packages structure needs it's first pass on organization.
  - the higher level packages should be converted into build modules in order to protect the various parts of the code from eachother.
  - the root dependency is the UseCase package so it should not depend on other modules. this is where the business rules live and where _the important_ testing goes.
  - services should not depend on other services (predictibility/mutability/extensibility)
 
# General concepts
- module dependency hierarchy: view -> viewmodel -> usecase <- service
- usecases tests validate business rules
- viewmodel tests validates UI rules
- services and views should not make decisions or interpret information
- services transmit data using their own DTO that interprets into app usecase API modeling. 

# Big ticket functional tech items for the near future
- ~~error handling~~
- analaytics service
- see issues for more
- i'd like to build out the wiki in this GitHub project with architecture and design documentation along with some related educational direction.

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
APIs [celebrate hands]
