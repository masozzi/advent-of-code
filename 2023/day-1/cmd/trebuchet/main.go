package main

import (
	"bufio"
	"log"
	"os"
	"strconv"
	"strings"
	"unicode"
)

func main() {
	file, err := os.Open("input.txt")
	if err != nil {
		log.Panicln("ERROR:", err.Error())
	}
	defer file.Close()

	r := bufio.NewReader(file)
	var sum1, sum2 int64 = 0, 0
	for line, err := r.ReadSlice('\n'); err == nil; line, err = r.ReadSlice('\n') {
		sum1 += part1(line)
		sum2 += part2(string(line))
	}

	if err != nil {
		log.Panicln("ERROR:", err.Error())
	}

	log.Println("RESULT part 1:", sum1)
	log.Println("RESULT part 2:", sum2)
}

func part1(line []byte) (res int64) {
	num := make([]byte, 1, 2)
	for _, b := range line {
		if !unicode.IsDigit(rune(b)) {
			continue
		}

		if len(num) > 1 {
			num[1] = b
		} else {
			num[0] = b
			// Prepopulate both values to account for cases where
			// the string has only one number (e.g.:two1nine).
			num = append(num, b)
		}
	}

	res, _ = strconv.ParseInt(string(num), 10, 64)
	return
}

func part2(line string) (res int64) {
	num := make([]byte, 1, 2)
	for i := 0; i < len(line); {
		b := line[i]
		if !unicode.IsDigit(rune(b)) {
			index, val := findString(line[i:])
			if index < 0 {
				i++
				continue
			}

			i += index
			b = val
		}

		if len(num) > 1 {
			num[1] = b
		} else {
			num[0] = b
			num = append(num, b)
		}
		i++
	}

	res, _ = strconv.ParseInt(string(num), 10, 64)
	return
}

func findString(line string) (int, byte) {
	digits := [...]string{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"}
	for i, d := range digits {
		if !strings.HasPrefix(line, d) {
			continue
		}

		// len(d) - 2 to adjust for the overlap between chars.
		// e.g.: oneight (can be both 1 and 8).
		return len(d) - 2, byte(i + 49)
	}

	return -1, 0
}
