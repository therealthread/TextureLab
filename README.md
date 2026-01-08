# TextureLab
A custom Resource Pack generator and viewer for Minecraft: Java Version, developed in Java and using its own JFX canvas-based UI system.
EDIT: The system may undergo design changes... I'm trying to keep it as original as possible.

![](https://github.com/therealthread/TextureLab/blob/main/view.png?raw=true)

# Node-based Editor
Experience Minecraft models like never before with the node‑based editor system!

![](https://github.com/therealthread/TextureLab/blob/main/node.png?raw=true)

![](https://github.com/therealthread/TextureLab/blob/main/node-editor.gif?raw=true)

on youtube: https://www.youtube.com/watch?v=GtUOZYtwErk

# CustomModelData (Example TextureLab's Lib from Anemys/@therealthread)

Okay... let me first explain why this system is necessary. Minecraft added a feature in its latest updates that allows us to completely customize item models. Yes, most of us will still continue to use the old, simple models/items, but they don't realize how much treasure they're missing... You can examine the Java code and its output below. This library I developed can, for example: change the models of items with certain enchantments (a simple example of a conditional modeling system), or give damaged items a progressively damaged appearance... or apply a player's team color to their sword/pickaxe or axe and only apply it to the item they are holding... Yes, Minecraft's modeling system has changed a lot, and most of us don't even realize it...

You can't directly own the library; I'm continuing to simplify this as much as possible with the node editor system I'm developing, and I hope you can access this application without any problems...

And yes, the app is completely free.

```java
public static void main(String[] args) {

        MultiComponentConditionModel<EnchantmentEntry> fire_aspect =
                new MultiComponentConditionModel<>(
                        ComponentTypes.ENCHANTMENTS,
                        List.of(
                                new EnchantmentEntry(
                                        List.of(EnchantmentTypes.FIRE_ASPECT),
                                        IntRange.min(1)
                                ),
                                new EnchantmentEntry(
                                        List.of(EnchantmentTypes.FIRE_ASPECT),
                                        IntRange.min(1)
                                ),
                                new EnchantmentEntry(
                                        List.of(EnchantmentTypes.FIRE_ASPECT),
                                        IntRange.min(1)
                                )
                        ),
                        new SimpleModel("anemys:item/fire"),
                        new Empty()
                );

        MultiComponentConditionModel<EnchantmentEntry> sharpness =
                    new MultiComponentConditionModel<>(
                            ComponentTypes.ENCHANTMENTS,
                            List.of(
                                    new EnchantmentEntry(
                                            List.of(EnchantmentTypes.SHARPNESS, EnchantmentTypes.FORTUNE),
                                            IntRange.min(1)
                                    ),
                                    new EnchantmentEntry(
                                            List.of(EnchantmentTypes.FIRE_ASPECT),
                                            IntRange.min(1)
                                    )
                            ),
                            new SimpleModel("anemys:item/sharpness"),
                            new Empty()
                    );

        SingleComponentConditionModel<TrimEntry> trimCondition = new SingleComponentConditionModel<>(
                ComponentTypes.TRIM,
                TrimEntry.pattern(TrimPatternTypes.EYE),
                fire_aspect,
                sharpness
        );

        CompositeModel compositeModel = new CompositeModel(List.of(
                new SimpleModel("anemys:item/stone_sword_1"), trimCondition, fire_aspect
        ));
        CompositeModel compositeModel2 = new CompositeModel(List.of(
                new SimpleModel("anemys:item/stone_sword_2"), sharpness, fire_aspect
        ));
        CompositeModel compositeModel3 = new CompositeModel(List.of(
                new SimpleModel("anemys:item/stone_sword_3"), sharpness, fire_aspect
        ));
        CompositeModel compositeModel4 = new CompositeModel(List.of(
                new SimpleModel("anemys:item/stone_sword_4"), sharpness, fire_aspect
        ));
        CompositeModel compositeModel5 = new CompositeModel(List.of(
                new SimpleModel("anemys:item/stone_sword_5"), sharpness, fire_aspect
        ));

        RangeDispatchModel rangeDispatch = new RangeDispatchModel(
                new NumericProperty.Damage(true),
                0.3f,
                List.of(
                        new RangeEntry(0, compositeModel),
                        new RangeEntry(20, compositeModel2),
                        new RangeEntry(40, compositeModel3),
                        new RangeEntry(60, compositeModel4),
                        new RangeEntry(80, compositeModel5)
                ),
                new SimpleModel("anemys:item/fallback")
        );

        ModelData bow = new SimpleModel("minecraft:item/bow");
        ModelData crossbow = new SimpleModel("minecraft:item/crossbow");

        SelectModel mainModel = new SelectModel(
                new SelectProperty.ChargeType(), List.of(new SelectCase("arrow", bow), new SelectCase("rocket", crossbow)),

                crossbow
        );

        SelectModel generic = new SelectModel(
                new SelectProperty.CustomModelData(0), List.of(new SelectCase("test", mainModel)), crossbow);

        CustomModelData cmd = new CustomModelData("minecraft:item/crossbow",
                false, true, 1.0f, generic, rangeDispatch);

        System.out.println(ModelSerializer.toJson(cmd).toString(2));
    }
```

out:
```
{
  "oversized_in_gui": true,
  "swap_animation_scale": 1,
  "model": {
    "cases": [{
      "model": {
        "cases": [
          {
            "model": {
              "model": "minecraft:item/bow",
              "type": "minecraft:model"
            },
            "when": "arrow"
          },
          {
            "model": {
              "model": "minecraft:item/crossbow",
              "type": "minecraft:model"
            },
            "when": "rocket"
          }
        ],
        "property": "minecraft:charge_type",
        "type": "minecraft:select",
        "fallback": {
          "model": "minecraft:item/crossbow",
          "type": "minecraft:model"
        }
      },
      "when": "test"
    }],
    "property": "minecraft:custom_model_data",
    "index": 0,
    "type": "minecraft:select",
    "fallback": {
      "model": "minecraft:item/crossbow",
      "type": "minecraft:model"
    }
  },
  "fallback": {
    "entries": [
      {
        "threshold": 0,
        "model": {
          "models": [
            {
              "model": "anemys:item/stone_sword_1",
              "type": "minecraft:model"
            },
            {
              "predicate": "minecraft:trim",
              "on_false": {
                "predicate": "minecraft:enchantments",
                "on_false": {"type": "minecraft:empty"},
                "on_true": {
                  "model": "anemys:item/sharpness",
                  "type": "minecraft:model"
                },
                "property": "minecraft:component",
                "type": "minecraft:condition",
                "value": [
                  {
                    "enchantments": [
                      "minecraft:sharpness",
                      "minecraft:fortune"
                    ],
                    "levels": {"min": 1}
                  },
                  {
                    "enchantments": "minecraft:fire_aspect",
                    "levels": {"min": 1}
                  }
                ]
              },
              "on_true": {
                "predicate": "minecraft:enchantments",
                "on_false": {"type": "minecraft:empty"},
                "on_true": {
                  "model": "anemys:item/fire",
                  "type": "minecraft:model"
                },
                "property": "minecraft:component",
                "type": "minecraft:condition",
                "value": [
                  {
                    "enchantments": "minecraft:fire_aspect",
                    "levels": {"min": 1}
                  },
                  {
                    "enchantments": "minecraft:fire_aspect",
                    "levels": {"min": 1}
                  },
                  {
                    "enchantments": "minecraft:fire_aspect",
                    "levels": {"min": 1}
                  }
                ]
              },
              "property": "minecraft:component",
              "type": "minecraft:condition",
              "value": {"pattern": "minecraft:eye"}
            },
            {
              "predicate": "minecraft:enchantments",
              "on_false": {"type": "minecraft:empty"},
              "on_true": {
                "model": "anemys:item/fire",
                "type": "minecraft:model"
              },
              "property": "minecraft:component",
              "type": "minecraft:condition",
              "value": [
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                }
              ]
            }
          ],
          "type": "minecraft:composite"
        }
      },
      {
        "threshold": 20,
        "model": {
          "models": [
            {
              "model": "anemys:item/stone_sword_2",
              "type": "minecraft:model"
            },
            {
              "predicate": "minecraft:enchantments",
              "on_false": {"type": "minecraft:empty"},
              "on_true": {
                "model": "anemys:item/sharpness",
                "type": "minecraft:model"
              },
              "property": "minecraft:component",
              "type": "minecraft:condition",
              "value": [
                {
                  "enchantments": [
                    "minecraft:sharpness",
                    "minecraft:fortune"
                  ],
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                }
              ]
            },
            {
              "predicate": "minecraft:enchantments",
              "on_false": {"type": "minecraft:empty"},
              "on_true": {
                "model": "anemys:item/fire",
                "type": "minecraft:model"
              },
              "property": "minecraft:component",
              "type": "minecraft:condition",
              "value": [
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                }
              ]
            }
          ],
          "type": "minecraft:composite"
        }
      },
      {
        "threshold": 40,
        "model": {
          "models": [
            {
              "model": "anemys:item/stone_sword_3",
              "type": "minecraft:model"
            },
            {
              "predicate": "minecraft:enchantments",
              "on_false": {"type": "minecraft:empty"},
              "on_true": {
                "model": "anemys:item/sharpness",
                "type": "minecraft:model"
              },
              "property": "minecraft:component",
              "type": "minecraft:condition",
              "value": [
                {
                  "enchantments": [
                    "minecraft:sharpness",
                    "minecraft:fortune"
                  ],
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                }
              ]
            },
            {
              "predicate": "minecraft:enchantments",
              "on_false": {"type": "minecraft:empty"},
              "on_true": {
                "model": "anemys:item/fire",
                "type": "minecraft:model"
              },
              "property": "minecraft:component",
              "type": "minecraft:condition",
              "value": [
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                }
              ]
            }
          ],
          "type": "minecraft:composite"
        }
      },
      {
        "threshold": 60,
        "model": {
          "models": [
            {
              "model": "anemys:item/stone_sword_4",
              "type": "minecraft:model"
            },
            {
              "predicate": "minecraft:enchantments",
              "on_false": {"type": "minecraft:empty"},
              "on_true": {
                "model": "anemys:item/sharpness",
                "type": "minecraft:model"
              },
              "property": "minecraft:component",
              "type": "minecraft:condition",
              "value": [
                {
                  "enchantments": [
                    "minecraft:sharpness",
                    "minecraft:fortune"
                  ],
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                }
              ]
            },
            {
              "predicate": "minecraft:enchantments",
              "on_false": {"type": "minecraft:empty"},
              "on_true": {
                "model": "anemys:item/fire",
                "type": "minecraft:model"
              },
              "property": "minecraft:component",
              "type": "minecraft:condition",
              "value": [
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                }
              ]
            }
          ],
          "type": "minecraft:composite"
        }
      },
      {
        "threshold": 80,
        "model": {
          "models": [
            {
              "model": "anemys:item/stone_sword_5",
              "type": "minecraft:model"
            },
            {
              "predicate": "minecraft:enchantments",
              "on_false": {"type": "minecraft:empty"},
              "on_true": {
                "model": "anemys:item/sharpness",
                "type": "minecraft:model"
              },
              "property": "minecraft:component",
              "type": "minecraft:condition",
              "value": [
                {
                  "enchantments": [
                    "minecraft:sharpness",
                    "minecraft:fortune"
                  ],
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                }
              ]
            },
            {
              "predicate": "minecraft:enchantments",
              "on_false": {"type": "minecraft:empty"},
              "on_true": {
                "model": "anemys:item/fire",
                "type": "minecraft:model"
              },
              "property": "minecraft:component",
              "type": "minecraft:condition",
              "value": [
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                },
                {
                  "enchantments": "minecraft:fire_aspect",
                  "levels": {"min": 1}
                }
              ]
            }
          ],
          "type": "minecraft:composite"
        }
      }
    ],
    "normalize": true,
    "property": "minecraft:damage",
    "scale": 0.3,
    "type": "minecraft:range_dispatch",
    "fallback": {
      "model": "anemys:item/fallback",
      "type": "minecraft:model"
    }
  },
  "hand_animation_on_swap": false
}
```

  (I might have made the list a bit confusing, but it can stay like this for now :7)
