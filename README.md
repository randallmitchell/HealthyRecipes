Before compiling, create a json file at
```
~/app/src/main/res/raw/local_recipes.json
```
using this format

```
{
  "recipes": [
    {
      "id": "1",
      "description": "zesty chickpea salad",
      "servings": "1 serving | make 4",
      "ingredients": [
        {
          "units": "1.5",
          "unit-type": "tablespoon",
          "name":  "olive oil"
        }
      ],
    }
  ]
}
```
This was not added to source control so as
to protect intellectual property 
