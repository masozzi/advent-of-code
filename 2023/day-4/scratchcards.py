import math

def part1(lines):
    sum = 0
    for line in lines:
        card_id, game = line.split(":")
        num, card = game.split("|")
        num = num.split()
        card = card.split()

        count = len(list(filter(lambda n: n in card, num)))

        pow = math.pow(2, count-1) if count > 0 else 0
        sum += pow

    print("RESULT:", sum)


def part2(lines):
    cards = {a: 1 for a in range(len(lines))}
    for index, line in enumerate(lines):
        card_id, game = line.split(":")
        num, card = game.split("|")
        num = num.split()
        card = card.split()

        count = len(list(filter(lambda n: n in card, num)))
        print("INDEX", index, "COUNT", count, "TOT", cards[index])
        for i in range(count):
            cards[index+i+1] += cards[index]

    print("RESULT:", sum(cards.values()))


if __name__ == "__main__":
    f = open("input.txt")
    lines = f.readlines()

    part1(lines)
    part2(lines)
