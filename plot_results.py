import os
import pandas as pd
import matplotlib.pyplot as plt
CSV_PATH = "results/results.csv"
PLOTS_DIR = "results/plots"
os.makedirs(PLOTS_DIR, exist_ok=True)
df = pd.read_csv(CSV_PATH)
w1 = df[df["workload"] == "W1"]
plt.figure()
for structure in w1["structure"].unique():
    data = w1[w1["structure"] == structure]
    plt.plot(
        data["n"],
        data["time_ms"],
        marker="o",
        label=structure
    )
plt.xlabel("n")
plt.ylabel("Time (ms)")
plt.title("W1 - Random Access")
plt.legend()
plt.grid()
plt.savefig(
    os.path.join(
        PLOTS_DIR,
        "W1_time.png"
    )
)
plt.close()
plt.figure()
for structure in w1["structure"].unique():
    data = w1[w1["structure"] == structure]
    plt.plot(
        data["n"],
        data["steps"],
        marker="o",
        label=structure
    )
plt.xlabel("n")
plt.ylabel("Steps")
plt.title("W1 - Steps")
plt.legend()
plt.grid()
plt.savefig(
    os.path.join(
        PLOTS_DIR,
        "W1_steps.png"
    )
)
plt.close()
w2 = df[df["workload"] == "W2"]
plt.figure()
for structure in w2["structure"].unique():
    data = w2[w2["structure"] == structure]
    plt.plot(
        data["n"],
        data["time_ms"],
        marker="o",
        label=structure
    )
plt.xlabel("n")
plt.ylabel("Time (ms)")
plt.title("W2 - Search")
plt.legend()
plt.grid()
plt.savefig(
    os.path.join(
        PLOTS_DIR,
        "W2_time.png"
    )
)
plt.close()
plt.figure()
for structure in w2["structure"].unique():
    data = w2[w2["structure"] == structure]
    plt.plot(
        data["n"],
        data["comparisons"],
        marker="o",
        label=structure
    )
plt.xlabel("n")
plt.ylabel("Comparisons")
plt.title("W2 - Comparisons")
plt.legend()
plt.grid()
plt.savefig(
    os.path.join(
        PLOTS_DIR,
        "W2_comparisons.png"
    )
)
plt.close()
for variant in ["head", "middle"]:
    w3 = df[
        (df["workload"] == "W3") &
        (df["variant"] == variant)
    ]
    plt.figure()
    for structure in w3["structure"].unique():
        data = w3[
            w3["structure"] == structure
        ]
        plt.plot(
            data["n"],
            data["time_ms"],
            marker="o",
            label=structure
        )
    plt.xlabel("n")
    plt.ylabel("Time (ms)")
    plt.title(
        f"W3 - {variant} - Insert & Remove"
    )
    plt.legend()
    plt.grid()
    plt.savefig(
        os.path.join(
            PLOTS_DIR,
            f"W3_{variant}_time.png"
        )
    )
    plt.close()
w4 = df[df["workload"] == "W4"]
plt.figure()
plt.plot(
    w4["n"],
    w4["time_ms"],
    marker="o"
)
plt.xlabel("n")
plt.ylabel("Time (ms)")
plt.title("W4 - MinHeap")
plt.grid()
plt.savefig(
    os.path.join(
        PLOTS_DIR,
        "W4_time.png"
    )
)
plt.close()
print("Plots created successfully.")