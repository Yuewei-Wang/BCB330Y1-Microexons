import csv
import gzip
from TruncateJunction import chromo_number_set
from Bio import SeqIO


with open("input/out_Microexons.csv", 'r') as csv_file:
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
        new_dict[elements[0]] = elements[3:]
        line_count += 1
print("Read exon coordinates file result: ")
print(csv_file.closed)


# find set_number of junction sequences by moving 10 nucleotides
def find_seq_by_coordinate(gene_name, chr_number, location, set_number):
    coor_list = [int(new_dict[gene_name][0]), int(new_dict[gene_name][1]),
                 int(new_dict[gene_name][2]), int(new_dict[gene_name][3])]
    pos_pos_lst = [[30, 30], [31, 29], [30, 30], [31, 29]]
    neg_pos_lst = [[31, 29], [30, 30], [31, 29], [30, 30]]
    coor = coor_list[location]
    strand = new_dict[gene_name][4]
    seq_file = "Homo_sapiens.GRCh38.dna.chromosome." + chr_number + ".fa.gz"
    result = [gene_name]
    with gzip.open(seq_file, "rt") as fasta_file:
        for record in SeqIO.parse(fasta_file, "fasta"):
            if strand == "-1\n" or strand == "-1":
                coor = coor_list[3 - location]  # reverse
                for i in range(set_number):
                    result.append(record.seq[coor-neg_pos_lst[location][0]-(i*10):
                                             coor+neg_pos_lst[location][1]-(i*10)]
                                  .reverse_complement())
            else:
                for j in range(set_number):
                    result.append(
                        record.seq[coor-pos_pos_lst[location][0]+(j * 10):
                                   coor+pos_pos_lst[location][1]+(j * 10)])
    if not fasta_file.closed:
        print("ERROR: file is not properly closed after used.")
    return result


for i in range(1, 5):
    with open("junction" + str(i) + "_shifting_set.fasta", 'w') as ofile:
        locations = ["3\' C1 junction", "5\' A junction",
                     "3\' A junction", "5\' C2 junction"]
        for each in chromo_number_set:
            result = find_seq_by_coordinate(each, chromo_number_set[each],
                                            i - 1, 5)
            if len(result) != 6:
                print("wrong number of junction sets\n")
            for j in range(1, len(result)):
                ofile.write(
                    ">" + result[0] + " " + locations[i - 1] + " set " + str(j)
                    + "\n" + str(result[j]) + "\n")
    print("Create junctions file result: ")
    print(ofile.closed)
