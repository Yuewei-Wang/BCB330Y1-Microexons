This is the program for BCB330Y1 Microexons project

-------------------------------------------------------
    Main functions (Java program part)
-------------------------------------------------------
1. Extract and classify the information from dot bracket files and 
    other relative txt files
2. Calculate the %base-pairing in different situations (i.e. micro-exon 
    over the whole sequence with upstream and downstream exons, 
    60-nt junction sequence)
3. Export the informative result into csv file, which are used for later-on 
    python analysis
    
-------------------------------------------------------
    Main functions (Python program part)
-------------------------------------------------------
1. Truncate the junction sequences according to the coordinate obtained from 
    previous steps 
2. Arrange the information from out files into csv files for statistical analysis

-------------------------------------------------------
    Extra information
-------------------------------------------------------
1. FileReader package in src is the collection of input files for Java part and Out 
    package contains the output result files.
2. ShiftSeq.py is not official used in the current phase of the project. It is 
    a fundamental version code for shifting nucleotide from 5' to 3' direction.
    It could be updated in future analysis.