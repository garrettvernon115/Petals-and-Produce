# Frontend

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 18.2.6.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via a platform of your choice. To use this command, you need to first add a package that implements end-to-end testing capabilities.

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.dev/tools/cli) page.


## Front-End Design Guidelines

### Color Palette
We are using a soft, nature-inspired palette from [Coolors.co](https://coolors.co/palette/ccd5ae-e9edc9-fefae0-faedcd-d4a373) to create a welcoming and fresh aesthetic for the Petals & Produce app.

| Variable Name        | Color     | Hex Code   | Purpose                          |
|----------------------|-----------|------------|----------------------------------|
| `--sage`             | Sage      | `#CCD5AE`  | Accent/secondary highlights      |
| `--mint`             | Mint      | `#E9EDC9`  | Background color                 |
| `--butter`           | Butter    | `#FEFAE0`  | Border accents or section bg     |
| `--apricot`          | Apricot   | `#FAEDCD`  | Default button background        |
| `--terra`            | Terra Cotta | `#D4A373` | Primary brand color, button hover |
| `--text-color`       | Deep Brown| `#3e2a2a`  | Primary text color               |

These colors are declared as CSS variables in the global `styles.css` file for consistent reuse across all components.

### Button Styling
Buttons use:
- `--button-bg`: `--apricot` for default
- `--button-hover-bg`: `--terra` for hover
- `--button-text-color`: `--text-color` for contrast

To apply styles, simply use the defined variables in component styles or global stylesheets.

