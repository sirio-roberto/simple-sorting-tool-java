# Sorting Utility
The Sorting Utility is a Java command-line application that provides various sorting functionalities for different data types. Users can specify the sorting type and data type, input and output files (optional), and the utility will perform sorting accordingly.

## Features
* Sort by Count / Natural: Users can choose between sorting by count or natural sorting.
* Data Types: Supports sorting for words, lines, and long numbers.
* Input and Output Files: Optional input and output file paths can be provided for processing.
* Display Results: Outputs the sorted data or results to the console or specified output file.

## Usage
1. Compile the Java files:
    ```
    javac sorting/*.java
    ```
2. Run the Main class to start the utility with arguments:
    ```
    java sorting.Main -sortingType <sorting_type> -dataType <data_type> -inputFile <input_file> -outputFile <output_file>
    ```
   * `<sorting_type>`: Specify sorting type (`byCount` or `natural`).
   * `<data_type>`: Specify data type (`word`, `line`, or `long`).
   * `<input_file>`: Optional - input file containing data for sorting.
   * `<output_file>`: Optional - specify an output file to save results.

3. View the sorted results on the console or in the specified output file.

* `-sortingType <sorting_type>`: Specify the sorting type (`byCount` or `natural`).
* `-dataType <data_type>`: Specify the data type (`word`, `line`, or `long`).
* `-inputFile <input_file>`: Optional - input file containing data for sorting.
* `-outputFile <output_file>`: Optional - specify an output file to save results.

## Example
Sort words naturally from an input file and display the result on the console:
```
java sorting.Main -sortingType natural -dataType word -inputFile input.txt
```

## Notes
* `<input_file>` and `<output_file>` should be valid file paths.
* Invalid input data (e.g., non-numeric values for long sorting) will be skipped.