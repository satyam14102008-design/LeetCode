class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        int n = arrival.length;
        int[] count = new int[k];

        TreeSet<Integer> available = new TreeSet<>();
        for (int i = 0; i < k; i++) available.add(i);

        PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        for (int i = 0; i < n; i++) {
            int time = arrival[i];

            while (!busy.isEmpty() && busy.peek()[0] <= time) {
                available.add(busy.poll()[1]);
            }

            if (available.isEmpty()) continue;

            int preferred = i % k;
            Integer server = available.ceiling(preferred);
            if (server == null) server = available.first();

            available.remove(server);
            count[server]++;
            busy.offer(new int[]{time + load[i], server});
        }

        int max = 0;
        for (int c : count) max = Math.max(max, c);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (count[i] == max) result.add(i);
        }
        return result;
    }
}