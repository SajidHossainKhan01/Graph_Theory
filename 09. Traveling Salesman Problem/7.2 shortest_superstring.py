from __future__ import annotations


def tsp(mat: list[list[float]], words: list[str], s: int, A: int, memo: dict[tuple[int,int],float], nxt: dict[tuple[int,int],int]) -> float:
    if (s, A) in memo:
        return memo[(s, A)]
    if not A:
        return len(words[s])
    else:
        memo[(s, A)], nxt[(s, A)] = min([(tsp(mat, words, i, A^(1<<i), memo, nxt)+mat[s][i], i) for i in range(len(mat)) if A&(1<<i)])
        return memo[(s, A)]


def tsp_dp(mat: list[list[float]], words: list[str], s: int) -> tuple[float,list[int]]:
    n: int = len(mat)
    A: int = (1<<n) - 1
    A ^= 1<<s
    memo: dict[tuple[int,int],float] = {}
    nxt: dict[tuple[int,int],int] = {}
    min_cost: float = tsp(mat, words, s, A, memo, nxt)
    min_tour: list[int] = [s]
    while A:
        s = nxt[(s, A)]
        min_tour.append(s)
        A ^= 1<<s
    return min_cost, min_tour


def calculate_cost(words: list[str], i: int, j: int) -> int:
    k: int
    for k in range(len(words[i])+1):
        if words[j].startswith(words[i][k:]):
            return k


def shortest_superstring(words: list[str]) -> str:
    words.append('')
    n: int = len(words)
    mat: list[list[float]] = [[0]*n for _ in range(n)]
    i: int; j: int
    for i in range(n):
        for j in range(n):
            mat[i][j] = calculate_cost(words, i, j)
    min_cost: float; p: list[int]
    min_cost, p = tsp_dp(mat, words, n-1)
    shortest: str = ''
    for i in range(len(p)-1):
        current_word: str; next_word: str
        current_word, next_word = words[p[i]], words[p[i+1]]
        k: int = mat[p[i]][p[i+1]]
        shortest += current_word[:k]
    shortest += words[p[-1]]
    return shortest


if __name__ == '__main__':
    words: list[str] = ['yuwkxomd', 'ldbcuseojg', 'dbcuseojgy', 'jgyim', 'owpyuwkx', 'mdqsldbcus']
    shortest: str = shortest_superstring(words)
    print(f'Shortest superstring: {shortest} (length: {len(shortest)})')

    # Output: Shortest superstring: owpyuwkxomdqsldbcuseojgyim (length: 26)
