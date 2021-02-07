import csv
import gzip
from Bio import SeqIO


chromo_number_set = dict()
chromo_number_set["ABI1"] = "10"
chromo_number_set["ANK2"] = "4"
chromo_number_set["AGRN"] = "1"
chromo_number_set["AP1G1"] = "16"
chromo_number_set["ANK3"] = "10"

with gzip.open("Homo_sapiens.GRCh38.dna.chromosome.10.fa.gz", "rt") as fasta_file:
    for record in SeqIO.parse(fasta_file, "fasta"):
        print(record.seq[26771075-1:26771089])  # coordinate for A of ABI1
        reverse = record.reverse_complement("10", "reverse Homo species "
                                                  "chromosome 10 dna")
        print(reverse.seq[len(reverse.seq)-26771089:
                          len(reverse.seq)-26771075+1])

with open("out_Microexons.csv", 'r') as csv_file:
    new_dict = dict()
    gene_name_list = []
    csv_reader = csv.reader(csv_file)
    line_count = 0
    for row in csv_file:
        if line_count == 0:
            title = row
        elements = row.split(",")
        if elements[0] not in gene_name_list:
            gene_name_list.append(elements[0])
        new_dict[elements[0]] = elements[3:]  #key is name, unique, what if repeated exoncoor
        line_count += 1
print("Read exon coordinates file result: ")
print(csv_file.closed)


def find_seq_by_coordinate(gene_name, chr_number):
    c1_intron = int(new_dict[gene_name][0])
    intron_a = int(new_dict[gene_name][1])
    a_intron = int(new_dict[gene_name][2])
    intron_c2 = int(new_dict[gene_name][3])
    strand = new_dict[gene_name][4]
    seq_file = "Homo_sapiens.GRCh38.dna.chromosome." + chr_number +".fa.gz"
    with gzip.open(seq_file, "rt") as fasta_file:
        for record in SeqIO.parse(fasta_file, "fasta"):
            if strand == "1\n" or strand == "1":
                first_junction = record.seq[c1_intron-31:c1_intron+29]
                second_junction = record.seq[intron_a-31:intron_a+29]
                third_junction = record.seq[a_intron-31:a_intron+29]
                fourth_junction = record.seq[intron_c2-31:intron_c2+29]
            else:
                reverse = record.reverse_complement()
                first_junction = reverse.seq[len(reverse.seq)-(c1_intron+31):
                                             len(reverse.seq)-(c1_intron-29)]
                second_junction = reverse.seq[len(reverse.seq)-(intron_a+31):
                                              len(reverse.seq)-(intron_a-29)]
                third_junction = reverse.seq[len(reverse.seq)-(a_intron+31):
                                             len(reverse.seq)-(a_intron-29)]
                fourth_junction = reverse.seq[len(reverse.seq)-(intron_c2+31):
                                              len(reverse.seq)-(intron_c2-29)]
    if not fasta_file.closed:
        print("ERROR: file is not properly closed after used.")
    return [gene_name, first_junction, second_junction, third_junction,
            fourth_junction]


with open('junction_results.csv', 'w') as output_csv_file:
    header = ["Gene Name", "3\' C1 junction", "5\' A junction",
              "3\' A junction", "5\' C2 junction"]
    cw = csv.DictWriter(output_csv_file, header)
    cw.writeheader()
    for each in chromo_number_set:
        result = find_seq_by_coordinate(each, chromo_number_set[each])
        row = {"Gene Name": result[0], "3\' C1 junction": result[1],
               "5\' A junction": result[2], "3\' A junction": result[3],
               "5\' C2 junction": result[4]}
        cw.writerow(row)
print("Create junctions file result: ")
print(output_csv_file.closed)

