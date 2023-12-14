# -*- coding: utf-8 -*-
import gzip
import json
import logging
import os
import shutil
import mysql.connector
from datetime import datetime

# 공통 변수
work_date = '20231206'
source_folder = work_date + '/source'
extract_folder_base = work_date + '/extract'
backup_folder_base = work_date + '/backup'
mysql_config = {
    'host': ' ',
    'user': ' ',
    'password': ' ',
    'database': ' '
}

# 로깅 설정
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s [%(levelname)s] %(message)s',
    handlers=[
        logging.StreamHandler(),
        logging.FileHandler('logfile.log')  # 로그 파일에 저장
    ]
)

def extract_gzip(gzip_file, extract_folder):
    with gzip.open(gzip_file, 'rt') as f_in:
        with open(extract_folder, 'w') as f_out:
            shutil.copyfileobj(f_in, f_out)

def read_json(json_file):
    # json_file이 파일인지 확인
    if os.path.isfile(json_file):
        with open(json_file, 'r') as file:
            data = json.load(file)
        return data
    else:
        logging.error(f"Cannot read JSON. {json_file} is not a file.")
        return None

def create_database():
    connection = mysql.connector.connect(**mysql_config)
    return connection

def insert_data_to_db(connection, entry):
    cursor = connection.cursor()
    query = """
        INSERT INTO page_access (cuid, startyn, referrer, location, devicetype, appType, created, referrertype, referrerhost, adtype, timestamp)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
        """
    timestamp_conv = convert_and_move(entry['timestamp'])
    # for entry in data:
    cursor.execute(query, (entry['cuid'], entry['startYn'], entry['referrer'], entry['location'], entry['deviceType'], entry['appType'], entry['created'], entry['referrerType'], entry['referrerHost'], entry['adtype'], timestamp_conv))
    connection.commit()


def backup_file(original_file, backup_file):
    shutil.move(original_file, backup_file)

def delete_work_file(gzip_file, extract_folder):
    # 작업 파일 및 폴더 삭제
    os.remove(gzip_file)
    shutil.rmtree(extract_folder)

def get_gzip_filename(gzip_file):
    # 파일 경로에서 파일명만 추출
    return os.path.basename(gzip_file)

def convert_to_datetime(date_string, formats):
    for fmt in formats:
        print (date_string)
        print (fmt)
        try:
            return datetime.strptime(date_string, fmt)
        except ValueError:
            pass
    raise ValueError("No valid date format found")

def convert_am_pm_string(date_string):
    translations = {
        '오전': 'AM',
        '오후': 'PM',
        '上午': 'AM',
        '下午': 'PM'
    }

    for k, v in translations.items():
        date_string = date_string.replace(k, v)

    return date_string

def convert_and_move(date_string):

    date_formats = ['%Y. %m. %d. %p %I:%M:%S', '%m/%d/%Y, %I:%M:%S %p', '%Y/%m/%d %p%I:%M:%S']
    # 2023. 11. 26. 오전 12:00:39
    # 11/22/2023, 10:42:23 AM
    # 2023/11/22 上午10:48:14

    # 오전/오후를 AM/PM으로 변경
    date_string = convert_am_pm_string(date_string)

    # 문자열을 datetime 객체로 변환
    dt_obj = convert_to_datetime(date_string, date_formats)

    #  다시 컨버팅 이동
    formatted_date = datetime.strftime(dt_obj, '%Y-%m-%d %H:%M:%S')
    return formatted_date

def main():

    original_date_string = '2023. 11. 26. 오전 12:00:39'
    formatted_date = convert_and_move(original_date_string)
    print(formatted_date)


    # 소스 폴더에서 모든 gzip 파일 찾기 (하위 폴더 포함)
    for root, dirs, files in os.walk(source_folder):
        for file in files:
            if file.endswith('.gz'):
                gzip_file = os.path.join(root, file)

                # 추출 폴더 생성
                # logging.info("source_folder : " + source_folder)
                # logging.info("os.path.relpath(gzip_file, source_folder) : " + os.path.dirname(os.path.relpath(gzip_file, source_folder)))
                extract_folder = os.path.join(extract_folder_base, os.path.dirname(os.path.relpath(gzip_file, source_folder))) + "/"
                os.makedirs(os.path.dirname(extract_folder), exist_ok=True)

                extract_file = extract_folder + '/' + get_gzip_filename(gzip_file).replace('.gz', '')
                logging.info("gzip_file : " + gzip_file)
                # logging.info("extract_folder : " + extract_folder)
                # logging.info("extract_file : " + extract_file)
                extract_gzip(gzip_file, extract_file)

                # JSON 파일 읽기
                json_file = os.path.join(extract_folder, get_gzip_filename(gzip_file).replace('.gz', ''))
                # logging.info("extfile : " + json_file)
                data = read_json(json_file)

                # 로그에 JSON 내용 출력
                logging.info("Processing file: {gzip_file}")
                logging.info("JSON Content:")
                logging.info(json.dumps(data, indent=4))

                # MySQL 데이터베이스 연결
                connection = create_database()

                # 데이터 삽입
                insert_data_to_db(connection, data)

                # 연결 종료
                connection.close()

                # 작업 파일 삭제
                delete_work_file(json_file, extract_folder)

                # 파일을 백업 폴더로 이동
                backup_folder = backup_folder_base + "/" + os.path.dirname(os.path.relpath(gzip_file, source_folder))
                backup_file_name = backup_folder_base + "/" + os.path.relpath(gzip_file, source_folder)
                # logging.info("backup_folder : " + backup_folder)
                os.makedirs(backup_folder, exist_ok=True)
                backup_file(gzip_file, backup_folder)

if __name__ == "__main__":
    main()