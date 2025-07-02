# PNM Image Editor

## Overview
PNM Image Editor is a basic yet extensible 2D image editor developed as part of a software engineering course project. The application supports the PNM family of image formats (PBM, PGM, PPM) and provides essential image transformation features. The project was developed using Agile methodologies (Scrum), with a strong focus on software design principles, testing, and user interaction best practices.

## Features
- **Load and display PNM images**: Supports PBM (black & white), PGM (grayscale), and PPM (color) formats, in both plain-text and binary forms.
- **Image transformation pipeline**: Prepare and execute a sequence of transformations, including:
  - Flip (upside-down and side-to-side)
  - Rotate 90° (clockwise and anti-clockwise)
  - Negative
- **Save images**: Persist modified images in any PNM format (PBM, PGM, PPM).
- **User preferences**: Store and load user preferences in a plain text file.
- **Multi-language support**: Change the UI and feedback language (English, Italian, ...).
- **Application info**: View version, build date, and developer credits.

## Technologies & Architecture
- **Language**: Java 17 LTS
- **GUI**: JavaFX
- **Build & Dependency Management**: Maven
- **Distribution**: Self-contained executable JAR
- **Architecture**: Two-layer modular design
  - **Backend**: Reusable server components, no graphical dependencies
  - **Frontend**: Client user interface
- **Extensibility**: Easily add new image formats and transformation algorithms

## Installation
### Prerequisites
- Java 17 LTS or newer
- Maven 3.6+

### Build Instructions
1. Clone the repository:
   ```sh
   git clone https://github.com/LorussoMarco/PNMImageEditor.git
   cd PNMImageEditor
   ```
2. Build the project with Maven:
   ```sh
   mvn clean package
   ```
3. The executable JAR will be located in `frontend/target/` (e.g., `frontend-<version>.jar`).

## Usage
1. Run the application:
   ```sh
   java -jar frontend/target/frontend-<version>.jar
   ```
2. Use the GUI to:
   - Open a PNM image from your filesystem
   - Add transformations to the pipeline and execute them
   - Save the edited image in your preferred PNM format
   - Change the application language in the preferences
   - View application info from the menu

## Testing
- **Unit tests**: Run with Maven:
  ```sh
  mvn test
  ```
- **End-to-end GUI tests**: Included in the test suite

## Extending the Editor
- **Add new image formats**: Implement a new handler and converter in the backend module.
- **Add new transformations**: Implement a new transformation strategy and corresponding frontend command.
- The modular design ensures new features can be integrated with minimal changes to existing code.

## Educational Objectives
This project demonstrates:
- Configuration management (versioning, dependencies, build, distribution)
- Requirements elicitation and management
- Software design principles and patterns
- User interaction principles
- Agile development (Scrum)
- Unit and end-to-end testing

## Credits
Developed by Group 04 for the Software Engineering course at SUPSI.

- [SUPSI - Netpbm reference](https://en.wikipedia.org/wiki/Netpbm)
- JavaFX, Maven

## License
This project is for educational purposes.
