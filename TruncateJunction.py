import csv
import gzip
from Bio import SeqIO


chromo_number_set = {"ABI1": "10", "ANK2": "4", "AGRN": "1", "AP1G1": "16",
                     "ANK3": "10", "AP1S2": "X", "APBB1": "11", "APBB2": "4",
                     "ASAP1": "8", "ASAP2": "2", "ATL1": "14", "ATL2": "2",
                     "CHCHD3": "7", "CLIP1": "12", "CYTH2": "19", "DCTN2": "12",
                     "DNM1": "9", "DOCK7": "1", "DOCK9": "13", "DTNA": "18",
                     "EMC1": "12", "ENAH": "1", "ERC1": "12", "ERGIC3": "20",
                     "FNBP1": "9", "FNBP1L": "1", "FRY": "13", "FRYL": "4",
                     "GDPD5": "11", "GRAMD1A": "19", "GULP1": "2", "HOOK3": "8",
                     "ITSN1": "21", "MACF1": "1", "MADD": "11", "MON2": "12",
                     "MYO18A": "17", "PTK2": "8", "SLC43A2": "17", "SPAG9": "17",
                     "SPTAN1": "9", "SSR1": "6", "VDAC3": "8", "VPS29": "12"}

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
        new_dict[elements[0]] = elements[3:]
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
            first_junction = record.seq[c1_intron-30:c1_intron+30]
            second_junction = record.seq[intron_a-31:intron_a+29]
            third_junction = record.seq[a_intron-30:a_intron+30]
            fourth_junction = record.seq[intron_c2-31:intron_c2+29]
            if strand == "-1\n" or strand == "-1":
                first_junction = record.seq[intron_c2-31:intron_c2+29].reverse_complement()
                second_junction = record.seq[a_intron-30:a_intron+30].reverse_complement()
                third_junction = record.seq[intron_a-31:intron_a+29].reverse_complement()
                fourth_junction = record.seq[c1_intron-30:c1_intron+30].reverse_complement()
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

