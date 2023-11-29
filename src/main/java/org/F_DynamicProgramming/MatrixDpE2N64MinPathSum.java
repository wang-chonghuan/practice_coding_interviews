package org.F_DynamicProgramming;

/*
given a m*n grid filled with non-negative numbers
fill a path from top left to bottom right
which minimizes the sum of numbers along its path
note: you can only move either down or right at any point in time

grid表示经过每个格子的代价,要求代价最小

这段代码使用了动态规划的方法来求解一个二维网格中从左上角到右下角的最小路径和。它使用一个额外的二维数组dp来保存从每个格子到终点的最小路径和。这里同样从右下角开始，逆向遍历至左上角，对于每一个格子，计算从这个格子到终点的最小路径和，并存储在dp中。对于非边界格子，它的值等于当前格子的值加上它下方格子和右方格子中的较小值；对于边界格子，由于其只有一种走法，值等于当前格子的值加上边界外的格子的值。遍历完所有格子后，dp[0][0]中储存了左上角格子到右下角格子的最小路径和。
-------------------------


非边缘、非角落位置，取右方和下方的最小路径和。这样不是贪婪吗,贪婪怎么能保证全局最优?或者不是贪婪,我理解不到位?好像也不是贪婪.

你的观察很尖锐。这个问题可能看起来像是贪婪算法，因为我们在每一步中都选择了一个局部最优的解（即选择一个使得从当前点到目标点的路径和最小的下一步）。然而，它其实是动态规划，我们可以通过下面的分析来理解这一点：

1. **最优子结构**：动态规划问题的一个关键特性是它们有最优子结构。在这个问题中，最优子结构体现在每个点 `(i, j)` 到终点的最小路径和可以从 `(i+1, j)` 和 `(i, j+1)` 中选择一个最小的加上 `grid[i][j]` 来获得。这是因为对于每一个点来说，到终点的最小路径一定也包含了其子问题（下一步）到终点的最小路径。

2. **无后效性**：一旦 `dp[i][j]` 被计算和存储在数组中，它就不依赖于任何未来的决策。换句话说，我们不必知道是如何到达 `(i, j)` 的，这就是所谓的无后效性。

3. **子问题重叠**：这个问题也有很多重叠的子问题，即我们在计算 `dp` 的过程中会反复计算多次相同点的最小路径和。通过保存这些结果避免重复计算，我们显著提高了算法的效率。

在贪婪算法中，我们在每一步中作出一个局部最优选择，不考虑这些选择如何影响未来的解。贪婪算法不保证我们找到的解是全局最优的。然而，在这个问题中，由于我们是从终点逆序回到起点，每一步的选择在全局上是最优的，因为它是基于所有可能的未来序列的最优解计算出来的。

因此，这个方法虽然在每一步中选择了局部最优解，但由于它考虑了从当前步到最终目标的所有可能路径（即它拥有最优子结构），它确保了找到的解是全局最优的。所以，这实际上是一个动态规划问题而非贪婪算法问题。
 */
public class MatrixDpE2N64MinPathSum {
    public int minPathSum(int[][] grid) {
        var m = grid.length;
        var n = grid[0].length;
        // 创建一个二维数组,用于存储每个位置到达右下角的最小路径和,这样就不用修改grid数组了
        int[][] dp = new int[m][n];
        // 从网格的右下角开始,向左上角遍历
        for(int i = m - 1; i >= 0; i--) {
            for(int j = n - 1; j >= 0; j--) {
                // 当在最下边的时候,只能向右移动
                if(i == m - 1 && j != n - 1) {
                    // 从 (i, j) 到右下角的最小路径和等于点 (i, j) 的值加上从其右侧相邻的点 (i, j + 1) 到右下角的最小路径和
                    dp[i][j] = grid[i][j] + dp[i][j+1];
                }
                // 当在最右边的时候,只能向下移动
                else if(i != m - 1 && j == n - 1) {
                    dp[i][j] = grid[i][j] + dp[i+1][j];
                }
                // 非边缘非角落的位置,取右方和下方的最小路径和
                else if(i != m - 1 && j != n - 1) {
                    dp[i][j] = grid[i][j] + Math.min(dp[i+1][j], dp[i][j+1]);
                }
                // 当在右下角的时候,没有可以移动的方向
                else {
                    dp[i][j] = grid[i][j];
                }
            }
        }
        return dp[0][0]; // 返回00的值,它表示从左上角到右下角的最小路径和
    }
}
