def pairs(num):
    i = iter(num)
    return zip(i, i)


def part1(lines):
    # Get seeds line
    seeds = [int(x) for x in lines.pop(0).split(":")[1].split()]
    print("SEEDS:", seeds)
    lines.pop(0)

    groups = list()
    group = list()
    for line in lines:
        if not line:
            groups.append(group)
            group = list()
            continue

        if not line[0].isdigit():
            continue

        group.append(line.split())

    for range_group in groups:
        su = seeds.copy()
        for group in range_group:
            dest, source, range = [int(x) for x in group]
            for index, seed in enumerate(seeds):
                if source <= seed < (source + range):
                    su[index] = seed + (dest - source)
                    continue

        seeds = su

    print("SEED:", min(seeds))


# Had to chec the solution.
def part2():
    f = open("input.txt")
    seeds, *mappings = f.read().split("\n\n")
    seeds = seeds.split(": ")[1]
    seeds = [int(x) for x in seeds.split()]
    seeds = [range(a, a + b) for a, b in pairs(seeds)]

    for m in mappings:
        _, *ranges = m.splitlines()
        ranges = [[int(x) for x in r.split()] for r in ranges]
        ranges = [(range(a, a + c), range(b, b + c)) for a, b, c in ranges]
        new_seeds = []

        for seed in seeds:
            for rdest, rsource in ranges:
                offset = rdest.start - rsource.start
                if seed.stop <= rsource.start or rsource.stop <= seed.start:
                    continue
                intersection = range(max(seed.start, rsource.start), min(seed.stop, rsource.stop))
                lr = range(seed.start, intersection.start)
                rr = range(intersection.stop, seed.stop)
                if lr:
                    seeds.append(lr)
                if rr:
                    seeds.append(rr)
                new_seeds.append(range(intersection.start + offset, intersection.stop + offset))
                break
            else:
                new_seeds.append(seed)

        seeds = new_seeds

    print("MIN", min(x.start for x in seeds))


if __name__ == "__main__":
    f = open("input.txt")
    lines = f.read().splitlines()

    part1(lines)
    part2()
