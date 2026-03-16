var inp1 = `
###############
#.......#....E#
#.#.###.#.###.#
#.....#.#...#.#
#.###.#####.#.#
#.#.#.......#.#
#.#.#####.###.#
#...........#.#
###.#.#####.#.#
#...#.....#.#.#
#.#.#.###.#.#.#
#....X#...#.#.#
#.###.#.#.#.#.#
#S..#.....#...#
###############`;
var inp11 = ``;
var inp2 = inp11.trim();
var inp3 = inp2.split('\n');
var map = inp3.map(l => l.split(''));
var sIdx = inp2.search('S');
var wdth = map[0].length;
var spos = {
    r: Math.trunc(sIdx / (wdth + 1)),
    c: sIdx % (wdth + 1),
};
var dirs = [
    {d: 'u', r: -1, c:  0},
    {d: 'r', r:  0, c:  1},
    {d: 'd', r:  1, c:  0},
    {d: 'l', r:  0, c: -1},
];
var vectorAngle = (pr, pc, r, c, nr, nc) => {
    var dot = (ax, bx, ay, by) => ax * bx + ay * by;
    var mag = (x, y) => Math.hypot(x, y);
    var v1 = {
        x: c - pc,
        y: r - pr
    };
    var v2 = {
        x: nc - c,
        y: nr - r
    };
    var angle = Math.acos(dot(v1.x, v2.x, v1.y, v2.y) / (mag(v1.x, v1.y) * mag(v2.x, v2.y)));

    return angle / Math.PI;
}
;
var getNextCost = (pqi, nd) => {
    if (isSameDir(pqi.dr, pqi.dc, nd.r, nd.c))
        return pqi.cst + 1;
    else
        return pqi.cst + 1001;
}
;
var isSameDir = (dr, dc, ndr, ndc) => {
    return ((dr === ndr) && (dc === ndc))
}
;
var isBackwardDir = (dr, dc, ndr, ndc) => {
    if (ndr === 0)
        return ndc === -dc;
    if (ndc === 0)
        return ndr === -dr;

    return false;
};

var getPossibleDirs = (ndr, ndc) => {
    return dirs.filter( (d) => !isBackwardDir(d.r, d.c, ndr, ndc));
};

var run1 = () => {
    var pq = [];
    pq.push({
        r: spos.r, c: spos.c, cst: 0,
        d: 'r', dr: 0, dc: 1,
    });
    var pqi = null;
    var nst = '';
    var nr = 0, nc = 0, ncst = 0;
    var costs = [];
    var vis = new Set();
    while (pq.length > 0) {
        pqi = pq.pop();
        if (map[pqi.r][pqi.c] === 'E') {
            costs.push(pqi.cst);
            continue;
        }
        if (vis.has(`${pqi.r},${pqi.c},${pqi.d}`))
            continue;
        vis.add(`${pqi.r},${pqi.c},${pqi.d}`);
        getPossibleDirs(pqi.dr, pqi.dc)
            .forEach((d) => {
                nr = pqi.r + d.r;
                nc = pqi.c + d.c;
                if (map[nr][nc] === '#')
                    return;
                ncst = getNextCost(pqi, d);
                pq.push({
                    r: nr, c: nc, cst: ncst,
                    d: d.d, dr: d.r, dc: d.c,
                });
            }
        );
        // poor man's PQ
        pq.sort( (a, b) => b.cst - a.cst);
    }
    console.log(costs);

    return Math.min(...costs);
}

run1();