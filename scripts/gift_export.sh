#!/bin/bash

# ログファイル名（日付付き）
LOG_FILE="/tmp/demo_wave/gift_export_$(date +'%Y%m%d_%H%M%S').log"

# プロファイル（必要に応じて変更）
SPRING_PROFILE=batch

# JARファイルのパス
JAR_PATH="/Users/rn-618/RikuGaneko/selfLearning/demo_wave/target/demo_wave-0.0.1-SNAPSHOT.jar"

# YYYYMM の形式にマッチする正規表現
ym_regex='^[0-9]{6}$'

# 引数が指定されている場合
if [[ -n "$1" ]]; then
    if [[ $1 =~ $ym_regex ]]; then
        TARGET_YEAR_MONTH=$1
        shift
    else
        echo "エラー: 日付は YYYYMM の形式で指定してください。例: 202507"
        exit 1
    fi
else
    # 指定がない場合は現在の年月を使用
    TARGET_YEAR_MONTH=$(date +'%Y%m')
fi

# 実行開始メッセージ
echo "=== Starting gift_export batch ===" | tee "$LOG_FILE"
echo "JAR: $JAR_PATH" | tee -a "$LOG_FILE"
echo "Profile: $SPRING_PROFILE" | tee -a "$LOG_FILE"
echo "TARGET_YEAR_MONTH: $TARGET_YEAR_MONTH" | tee -a "$LOG_FILE"

set -x

# バッチ実行
java -jar \
  -Dspring.profiles.active=$SPRING_PROFILE \
  -Dspring.main.web-application-type=none \
  "$JAR_PATH" gift.export \
  -year_month "$TARGET_YEAR_MONTH" "$@" 2>&1 | tee -a "$LOG_FILE"
status=${PIPESTATUS[0]}

set +x

# 結果チェック
if [ $status -eq 0 ]; then
  echo "✅ gift_export batch completed successfully." | tee -a "$LOG_FILE"
else
  echo "❌ gift_export batch failed with exit code $status" | tee -a "$LOG_FILE"
fi

exit $status