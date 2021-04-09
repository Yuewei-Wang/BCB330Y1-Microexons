import csv

file1 = open('junction_4_100shuffle.out', 'r')
Lines = file1.readlines()
print(len(Lines))
seq = dict()
energy = dict()
i = 0
for i in range(len(Lines)):
    if Lines[i][0] == '>':
        title_line = Lines[i].split(" ")
        if not title_line[0] in seq:
            seq[title_line[0]] = []
            energy[title_line[0]] = []
        seq[title_line[0]].append(Lines[i + 1])
        bracket_line = Lines[i + 2].split(" ")
        final = bracket_line[-1].strip("()\n")

        energy[title_line[0]].append(final)
    i = i + 1
file1.close()

with open('out.csv', 'w') as out:
    header = ["Gene Name", "ori seq"]
    for i in range(1, 101):
        header.append("shuffle" + str(i))
    header.append("ori energy")
    for i in range(1, 101):
        header.append("energy" + str(i))
    cw = csv.DictWriter(out, header)
    cw.writeheader()
    for each in seq:
        row = {"Gene Name": each[1:], "ori seq": seq[each][0],
               "ori energy":energy[each][0]}
        for i in range(1, 101):
            row["shuffle" + str(i)] = seq[each][i]
        for i in range(1, 101):
            row["energy" + str(i)] = energy[each][i]
        cw.writerow(row)
print("Create junctions file result: ")
print(out.closed)
