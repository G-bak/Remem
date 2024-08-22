from flask import Flask, request, jsonify
from konlpy.tag import Okt

app = Flask(__name__)

@app.route('/process', methods=['POST'])
def process_data():
    try:
        # 요청 데이터 로그
        print("Received Request Data:", request.json)
        
        if not request.json:
            return jsonify({"error": "Invalid JSON data"}), 400
        
        question = request.json.get('question')
        excluded_keywords = request.json.get('excludedKeywords', [])

        # 데이터와 불용어 리스트가 잘 전달되었는지 확인
        if question:
            print("Question received:", question)
        else:
            print("No question received")

        if excluded_keywords:
            print("Excluded Keywords received:", excluded_keywords)
        else:
            print("No excluded keywords received, using empty list")

        # 데이터 전처리 로직
        processed_data = data_processing_function(question, excluded_keywords)
        
        # 전처리 결과 로그
        print("Processed Data to be returned:", processed_data)
        
        return jsonify({"processed_data": processed_data})
    except Exception as e:
        print(f"An error occurred: {e}")
        return jsonify({"error": "Internal Server Error", "message": str(e)}), 500

def data_processing_function(data, excluded_keywords):
    # 데이터 전처리 작업
    print("Original Data:", data)
    processed_data = data  # 초기 데이터 할당

    # 불용어 리스트 정의
    korean_stop_words = set([
        '이', '그', '저', '그리고', '하지만', '그러나', '따라서', '그래서', 
        '즉', '하지만', '만약', '이와', '같이', '게다가', '또한', '그런데', 
        '한편', '은', '는', '이', '가', '을', '를', '에', '의', '와', '과', 
        '도', '으로', '에', '에서', '에게', '한테', '까지', '부터', '같은', '각'
    ])

    # 사용자 정의 불용어 추가 (JSON 데이터에서 가져옴)
    custom_stop_words = set(excluded_keywords)
    korean_stop_words.update(custom_stop_words)

    # Okt 형태소 분석기 사용
    okt = Okt()

    # 문장을 토큰화
    word_tokens = okt.morphs(data)
    print("Word Tokens:", word_tokens)
    
    # 불용어 제거
    filtered_sentence = [word for word in word_tokens if word not in korean_stop_words]
    print("Filtered Sentence:", filtered_sentence)
    
    # 명사 추출 (불용어 제거 후 추출)
    filtered_nouns = [word for word in word_tokens if word in okt.nouns(data) and word not in korean_stop_words]
    print("Filtered Nouns in Original Order:", filtered_nouns)
    
    # 결과를 다시 하나의 문자열로 결합
    filtered_text = ' '.join(filtered_nouns)
    print("Filtered Text:", filtered_text)
    
    return filtered_text

if __name__ == '__main__':
    app.run(port=5000)  # 포트 번호는 5000으로 설정