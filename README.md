# TicTacToe Game

A classic 
#TicTacToe game designed for Android devices, featuring adjustable difficulty levels and options to play against the CPU or another player.

## Table of Contents

- [Features](#features)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Usage](#usage)
- [Code Structure](#code-structure)
- [Dependencies](#dependencies)
- [Contributing](#contributing)
- [License](#license)
- [Contact](#contact)


## Features

- **Play Modes**: Play against another player or the CPU.
- **Difficulty Levels**: Choose between easy, medium, and hard difficulties.
- **Dynamic Board**: Adjust the board size and gameplay settings dynamically.
- **Real-Time Updates**: Displays the number of wins for each player and draws.

## Screenshots
![1.png](screenshot%2F1.png)
![2.png](screenshot%2F2.png)
![3.png](screenshot%2F3.png)
![4.png](screenshot%2F4.png)
![5.png](screenshot%2F5.png)
![6.png](screenshot%2F6.png)

## Installation

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/Uchyy/tictactoe.git
   cd tictactoe

2. **Open the Project in Android Studio**:

    - Import the project into Android Studio by opening the tictactoe directory.

3. **Build and Run**:

    - Build the project using Android Studio.
    - Connect an Android device or start an emulator.
    - Run the project from Android Studio to install and launch the app on your device.


## Usage

### Start the App

- Launch the TicTacToe app from your device.

### Choose Play Mode

- Select whether you want to play against another player or the CPU.

### Select Difficulty

- Use the slider to adjust the difficulty level between Easy, Medium, and Hard.

### Play the Game

- Tap on the cells in the board to make your move.
- The game will automatically handle turns between players and the CPU.

### Restart the Game

- Click the Restart button to reload the game board and reset the game state.

### Exit the App

- Use the back button to exit the game, with a confirmation dialog to prevent accidental exits.



## Code Structure

### `MainActivity`
- Handles the main game logic, UI setup, and user interactions.
    - Initializes game board and UI components.
    - Observes `LiveData` from `GameViewModel` for real-time updates.
    - Manages game state and player interactions.

### `GameViewModel`
- Provides data to the UI and handles game state.
    - Manages the game board, player scores, and current player.

### `GameManager`
- Resets the game state and manages game operations.

### `TTT`
- Contains the game logic for TicTacToe.

### `Player`
- Represents a player in the game, either human or CPU.

### `SetEditText`
- Sets properties for the `EditText` views in the game board.


## Dependencies

- **AndroidX**: For modern Android development practices.
- **Google Material Components**: For UI components like the slider.
- **Lifecycle & LiveData**: For managing and observing game state.

## Contributing

Contributions are welcome! If you have suggestions or improvements, please follow these steps:

1. Fork the repository.
2. Create a new branch: `git checkout -b feature/YourFeature`.
3. Commit your changes: `git commit -am 'Add new feature'`.
4. Push to the branch: `git push origin feature/YourFeature`.
5. Create a new Pull Request.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact

- **Author**: Uche Nwaobiala
- **Email**: [uchenwaobiala@gmail.com](mailto:uchenwaobiala@gmail.com)

      


   
