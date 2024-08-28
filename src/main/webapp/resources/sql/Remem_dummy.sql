-- USERS 테이블 생성: 사용자 정보를 저장
CREATE TABLE users (
    user_id VARCHAR2(20) PRIMARY KEY,       -- 사용자 ID, 기본 키
    user_name VARCHAR2(50) NOT NULL,        -- 사용자 이름 (필수)
    user_password VARCHAR2(100) NOT NULL,   -- 사용자 비밀번호 (필수)
    user_address VARCHAR2(1000),            -- 사용자 주소 (선택 사항)
    registration_date DATE DEFAULT SYSDATE  -- 사용자 등록 날짜 (기본값: 현재 날짜)
);

-- USERS 테이블에 데이터 삽입: 샘플 사용자 정보 추가
INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('jpt26', '최재빈', 'jaebin66', '충남 천안시 직산읍 삼은리');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('maru26', '강나은', 'naeun66', '충남 천안시 동남구 각원사길 26');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('minmonade', '이정민', 'jeongmin55', '충남 천안시 서북구 늘푸른1길 29 ');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('mintwo20', '김혜민', 'hyemin55', '충남 천안시 서북구 원두정2길 22');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('april0240', '서민주', 'minju55', '충남 천안시 서북구 쌍용5길 5');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('panda', '이세준', 'fubao66', '경기 평택시 평택로 2');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('haixiu', '강지은', 'shy99', '충남 천안시 성거읍 석교리');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('genius', '서원우', 'bbb11', '충남 천안시 동남구 단대로 12');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('tetris', '박상준', 'tsts77', '충남 아산시 곡교천로 28-10');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('fashion', '김규빈', 'clothes88', '충남 아산시 탕정면 갈산리');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('thirty', '윤정호', 'thirty30', '충남 아산시 곡교천로27번길 43');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('webtoon', '우희선', 'webtoon11', '충남 천안시 서북구 공원로 219');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('binary', '이진수', 'square22', '충남 천안시 동남구 충무로 105');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('maknae', '정지흠', 'youngest23', '충남 천안시 서북구 성정5길 15');

INSERT INTO users (user_id, user_name, user_password, user_address)
VALUES ('teacher', '정용진', 'laoshi44', '충남 아산시 권곡로 36-5');

-- USERS 테이블의 모든 데이터를 조회
-- SELECT * FROM users;

-- USER_DIARY 테이블 생성: 사용자의 일기 정보를 저장
CREATE TABLE user_diary (
    diary_id VARCHAR2(50) PRIMARY KEY,      -- 일기 ID, 기본 키
    user_id VARCHAR2(100) NOT NULL,         -- 사용자 ID (필수)
    diary_title VARCHAR2(1000) NOT NULL,    -- 일기 제목 (필수)
    write_date DATE NOT NULL,               -- 작성 날짜 (필수)
    diary_content CLOB NOT NULL             -- 일기 내용 (필수, CLOB 타입)
);

-- DIARY_ID 시퀀스 생성: 일기 ID를 자동 증가시키기 위한 시퀀스
CREATE SEQUENCE seq_diary_id
START WITH 1        -- 시작 값
INCREMENT BY 1      -- 증가 값
NOMAXVALUE          -- 최대 값 없음
NOCYCLE             -- 순환하지 않음
NOCACHE;            -- 캐시 사용 안 함

-- USER_DIARY 테이블의 모든 데이터를 조회
--SELECT * FROM user_diary;

-- USER_DIARY 테이블에 데이터 삽입: 샘플 일기 데이터 추가


INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('maru26' || seq_diary_id.nextval, 'maru26', '아르바이트 첫 출근', TO_DATE('2024-08-01', 'YYYY-MM-DD'), '오늘은 새로 시작한 카페 아르바이트의 첫 출근 날이었다. 아침 일찍 일어나 준비를 하고, 약간의 긴장감을 안고 카페에 도착했다. 매니저님이 나를 반갑게 맞아주셨고, 다른 직원들과 인사도 나눴다. 처음에는 다들 낯설었지만, 매니저님이 친절하게 업무를 설명해 주셔서 금세 적응할 수 있었다. 바리스타가 처음이라 커피를 만드는 일이 어려웠지만, 손님이 만족스러워하는 모습을 보니 뿌듯했다.
카페 일은 생각보다 힘들었다. 손님이 많아서 계속해서 주문을 받아야 했고, 서빙도 해야 했다. 특히 점심시간이 되니 손님들이 몰려와서 정신이 없었다. 그래도 일이 손에 익으면서 점점 자신감이 생겼다. 동료 직원들도 도와주고, 가르쳐주면서 분위기가 좋았다. 오후에는 좀 더 여유가 생겨서 손님들과 간단히 대화도 나눌 수 있었다. 특히 한 할머니 손님이 내게 친절하게 대해주셔서 기분이 좋았다. 오늘의 피곤함은 그만큼의 보람으로 보상받은 느낌이다.
퇴근 후에는 몸이 많이 지쳤지만, 하루를 잘 마무리했다는 성취감이 컸다. 앞으로 계속해서 이 아르바이트를 하면서 더 많은 것을 배우고, 능숙해지기를 기대해 본다. 무엇보다도 사람들과의 교류가 즐겁다는 점이 마음에 든다. 오늘은 처음이라서 긴장도 했지만, 점점 더 적응해 나갈 자신이 있다. 내일도 또 새로운 하루를 시작할 생각에 벌써부터 설렌다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('maru26' || seq_diary_id.nextval, 'maru26', '기숙사 친구들과의 밤', TO_DATE('2024-08-02', 'YYYY-MM-DD'), '오늘은 기숙사 친구들과 함께 저녁을 먹기로 했다. 각자 바쁜 일상 때문에 자주 만나지 못했지만, 오늘은 모처럼 시간을 맞출 수 있었다. 저녁으로는 기숙사 주방에서 간단한 요리를 하기로 했는데, 다들 요리에 익숙하지 않아서 우왕좌왕하는 모습이 재미있었다. 결과물은 엉망이었지만, 웃음이 끊이지 않았다. 이런 시간이야말로 기숙사 생활의 묘미가 아닐까 싶다.
저녁을 먹고 난 후, 다 같이 기숙사 라운지에 모여서 영화를 보기로 했다. 모두가 좋아하는 액션 영화를 골랐고, 팝콘을 만들어 함께 나눠 먹었다. 영화가 끝난 후에도 다들 떠나지 않고 남아 이야기를 나눴다. 각자 학교생활에서 있었던 일들, 고민들, 그리고 미래에 대한 이야기까지 자연스럽게 이어졌다. 그동안 미처 나누지 못했던 속마음을 털어놓으며 서로를 더 깊이 이해하게 된 것 같다.
밤이 깊어가면서, 우리는 라운지에서 기숙사 방으로 자리를 옮겼다. 여기저기에서 간식이 등장하고, 분위기는 점점 더 즐거워졌다. 친구들과 함께 있으면 시간 가는 줄 모르고 밤을 새우게 된다. 내일 아침 수업이 걱정되지만, 오늘 이 순간이 너무 소중해서 포기할 수 없었다. 이런 소소한 기쁨들이 대학 생활을 더욱 특별하게 만드는 것 같다. 나중에 졸업하고 나면 이 순간들이 그리워질 것 같다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('maru26' || seq_diary_id.nextval, 'maru26', '중간고사 준비', TO_DATE('2024-08-03', 'YYYY-MM-DD'), '오늘은 중간고사 준비로 하루를 보냈다. 아침부터 도서관에 가서 자리 잡고, 강의 노트와 교재를 펼쳐 들었다. 이번 학기에는 전공 수업이 많아서 준비할 내용이 방대하다. 그래서 시간 관리가 특히 중요하다. 먼저 오늘은 가장 어렵다고 생각한 과목부터 집중적으로 공부하기로 했다. 처음에는 복습이 잘 안 돼서 걱정했지만, 몇 시간 동안 집중하다 보니 조금씩 자신감이 생겼다.
오후에는 그룹 스터디를 위해 친구들과 모였다. 각자 맡은 부분을 발표하고, 서로 질문하면서 공부를 보완했다. 친구들의 설명을 들으면서 내가 놓쳤던 부분들을 채워 나갈 수 있었다. 그룹 스터디는 혼자 공부할 때보다 훨씬 효율적이라는 생각이 들었다. 무엇보다 함께 공부하는 친구들이 있어 든든했다. 스터디 후에는 친구들과 간단히 점심을 먹고 다시 도서관으로 돌아와 공부를 이어갔다.
저녁 시간이 되니 머리가 아프고 피로감이 몰려왔다. 그래서 잠시 쉬기로 하고 도서관 카페에서 커피를 마시며 기분 전환을 했다. 잠깐의 휴식 후 다시 공부를 시작했지만, 오늘은 많이 지친 것 같다. 남은 며칠 동안 효율적으로 공부할 수 있도록 계획을 다시 세워야겠다. 시험은 항상 스트레스지만, 그만큼 성장할 수 있는 기회라고 생각하고 긍정적으로 임해야겠다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('maru26' || seq_diary_id.nextval, 'maru26', '캠퍼스에서의 봄날', TO_DATE('2024-08-04', 'YYYY-MM-DD'), '오늘은 날씨가 너무 좋아서 강의가 끝나고 캠퍼스를 산책했다. 캠퍼스 곳곳에 벚꽃이 피어 있어서, 마치 꽃길을 걷는 기분이었다. 점심시간에는 친구들과 함께 벤치에 앉아 도시락을 먹으며 여유를 즐겼다. 따뜻한 햇살 아래에서 웃고 떠드는 시간이 얼마나 소중한지 새삼 느꼈다. 봄이 주는 이 기쁨을 마음껏 누리기로 했다.
산책을 하다가 학교 정문 앞에서 동아리 홍보를 하는 후배들을 만났다. 처음 입학했을 때가 생각나서 잠시 이야기를 나누었다. 후배들이 열심히 홍보하는 모습을 보니, 나도 다시 한번 열정을 되찾고 싶다는 생각이 들었다. 그래서 오랜만에 동아리 모임에 참석하기로 결심했다. 봄은 새로운 시작을 상징하는 계절이라 그런지, 나도 뭔가 새로운 도전을 해보고 싶다는 마음이 들었다.
집에 돌아와서 오늘 찍은 사진들을 정리하며 하루를 마무리했다. 벚꽃 아래서 찍은 사진들이 특히 예쁘게 나와서 기분이 좋았다. 오늘의 이 평화로운 순간들이 오래 기억에 남을 것 같다. 내일은 더 많은 사람들과 함께 이 봄의 기쁨을 나누고 싶다. 캠퍼스에서의 이런 소소한 행복이 대학 생활의 가장 큰 즐거움 중 하나인 것 같다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('maru26' || seq_diary_id.nextval, 'maru26', '새벽 독서의 매력', TO_DATE('2024-08-06', 'YYYY-MM-DD'), '오늘은 늦은 밤까지 과제를 하다가, 갑자기 독서가 하고 싶어졌다. 그래서 책장을 뒤져 오래전에 사둔 소설 한 권을 꺼냈다. 침대에 누워 조용히 책을 읽기 시작했는데, 이 시간이 너무나도 평화로웠다. 책 속의 이야기에 빠져들면서 현실의 모든 걱정을 잊을 수 있었다. 새벽 독서는 정말 특별한 매력이 있는 것 같다.
시간 가는 줄 모르고 책을 읽다 보니 어느새 새벽 3시가 되었다. 내일 아침 수업이 걱정되었지만, 책에서 눈을 뗄 수가 없었다. 결국, 책을 다 읽고 나서야 잠자리에 들었다. 오늘 읽은 소설은 내 마음속 깊이 감동을 남겼다. 때로는 이렇게 무작정 책 속에 빠져드는 시간이 필요하다는 걸 새삼 깨달았다. 독서는 내가 좋아하는 일 중 하나인데, 바쁘다는 이유로 자주 하지 못했던 것 같다.
내일은 조금 피곤하겠지만, 오늘의 이 시간이 너무 소중하게 느껴져서 후회하지 않는다. 앞으로는 더 자주 이런 시간을 가지기로 했다. 책 한 권이 주는 위로와 감동은 무엇과도 바꿀 수 없는 것 같다. 오늘 밤은 그 감동을 안고 깊이 잠들 수 있을 것 같다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('maru26' || seq_diary_id.nextval, 'maru26', '첫 발표의 긴장감', TO_DATE('2024-08-07', 'YYYY-MM-DD'), '오늘은 수업에서 첫 발표를 하는 날이었다. 아침부터 긴장이 되어 밥도 제대로 먹지 못하고 학교에 갔다. 발표 준비를 열심히 했지만, 막상 앞에 서니 심장이 두근거리고 머리가 하얘졌다. 그래도 준비한 자료를 바탕으로 침착하게 발표를 이어갔다. 교수님과 친구들의 시선이 부담스러웠지만, 최대한 떨지 않으려고 노력했다.
발표가 끝난 후 교수님께서 긍정적인 피드백을 주셔서 조금 안심할 수 있었다. 친구들도 잘했다고 칭찬해 주어서 기분이 좋았다. 첫 발표라서 많이 떨렸지만, 그래도 무사히 끝마쳤다는 사실에 스스로 자랑스러웠다. 발표를 통해 나의 생각을 정리하고, 다른 사람들과 공유하는 것이 얼마나 중요한지 깨달았다. 앞으로 더 많이 연습해서 발표를 잘 할 수 있도록 노력해야겠다.
오늘은 발표를 준비하면서 배운 것이 참 많았다. 발표 자료를 만들고, 내용을 정리하는 과정에서 나의 지식이 더 확고해졌다는 것을 느꼈다. 또한, 발표를 통해 다른 사람들과 소통하는 능력도 키울 수 있었다. 첫 발표는 어렵지만, 그만큼 많은 것을 얻을 수 있는 기회였다. 앞으로도 더 많은 발표 기회를 통해 성장할 수 있기를 바란다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('maru26' || seq_diary_id.nextval, 'maru26', '캠퍼스의 여름 밤', TO_DATE('2024-08-10', 'YYYY-MM-DD'), '오늘은 저녁 강의가 끝난 후, 친구들과 함께 캠퍼스를 거닐었다. 여름 밤의 시원한 바람이 우리를 반겨주었고, 기숙사 앞 잔디밭에 앉아 이야기를 나누었다. 밤하늘에 떠 있는 별들을 바라보며, 서로의 꿈과 목표에 대해 이야기했다. 이런 시간이야말로 대학 생활의 진정한 보람이라는 생각이 들었다.
친구들과의 대화는 끝이 없었다. 학업, 진로, 사랑, 그리고 앞으로의 인생에 대한 이야기까지 깊이 있는 대화를 나눴다. 평소에는 쉽게 꺼내지 못했던 고민들도 이 밤의 분위기 속에서 자연스럽게 이야기할 수 있었다. 서로의 생각을 듣고 공감하면서, 우리의 우정이 더욱 깊어지는 것을 느꼈다. 캠퍼스의 여름 밤은 그 자체로도 아름답지만, 이렇게 소중한 사람들과 함께라면 더욱 특별한 기억으로 남을 것 같다.
시간이 늦어지면서, 우리는 아쉬움을 남긴 채 기숙사로 돌아갔다. 오늘 밤의 대화는 오래도록 기억에 남을 것 같다. 앞으로도 종종 이렇게 모여서 이야기를 나누며 서로에게 힘이 되어주기로 약속했다. 대학 생활이 끝날 때까지, 이 소중한 우정이 계속 이어지기를 바란다. 오늘 밤의 이 평화로움과 행복함을 마음에 새기며 잠자리에 들었다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('maru26' || seq_diary_id.nextval, 'maru26', '수강 신청의 스트레스', TO_DATE('2024-08-13', 'YYYY-MM-DD'), '오늘은 수강 신청 날이었다. 아침 일찍부터 컴퓨터 앞에 앉아, 원하는 강의를 신청하기 위해 긴장된 마음으로 대기했다. 수강 신청은 항상 경쟁이 치열해서, 원하는 강의를 신청하는 것이 쉽지 않다. 그래서 미리 대체 강의도 몇 개 준비해 두었다. 9시 정각이 되자마자 빠르게 신청 버튼을 눌렀지만, 예상대로 몇몇 강의는 이미 마감되었다. 하지만 다행히도 대체 강의 중 몇 개를 성공적으로 신청할 수 있었다.
수강 신청이 끝나고 나니 피로가 몰려왔다. 이번 학기에는 전공 과목을 많이 들을 계획이었지만, 원하는 과목을 다 듣지 못해서 아쉬웠다. 하지만 남은 과목들로도 충분히 의미 있는 학기를 보낼 수 있을 것 같다. 수강 신청은 항상 스트레스를 받지만, 그만큼 잘 준비하면 원하는 과목을 들을 수 있는 기회가 되기도 한다. 이번 학기도 열심히 계획을 세워야겠다.
친구들과 함께 점심을 먹으면서 각자의 수강 신청 결과에 대해 이야기했다. 다들 나름대로 만족스러운 결과를 얻은 것 같아서 다행이다. 수강 신청은 매 학기 반복되지만, 그때마다 항상 긴장되고 스트레스를 받는다는 것이 참 신기하다. 그래도 친구들과 함께 준비하고, 서로 정보를 나누면서 조금이나마 부담을 덜 수 있었다. 앞으로도 이런 일들은 함께 하는 것이 중요하다는 것을 다시 한 번 느꼈다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('maru26' || seq_diary_id.nextval, 'maru26', '가을 학기 시작', TO_DATE('2024-08-15', 'YYYY-MM-DD'), '오늘은 가을 학기의 첫날이었다. 여름 방학 동안의 여유로운 시간을 뒤로하고 다시 학교로 돌아오니 기분이 묘했다. 새로운 교수님들과 새로운 강의가 기다리고 있어서 약간의 긴장감이 들었다. 첫 수업에서는 교수님들이 과목 소개와 함께 이번 학기에 해야 할 과제와 프로젝트를 설명해 주셨다. 기대 반, 걱정 반으로 수업을 들으며 이번 학기 계획을 머릿속으로 정리했다.
수업이 끝나고 도서관에 들러 필요한 교재를 빌리고, 집에 돌아와 강의 계획서를 찬찬히 읽어 보았다. 이번 학기에는 전공 과목이 많아서 더욱 집중해야 할 것 같다. 또한, 새로운 프로젝트도 준비해야 하기 때문에 체계적인 계획이 필요하다. 가을 학기는 늘 바쁘지만, 그만큼 많은 것을 배울 수 있는 기회라는 생각이 들었다. 이번 학기에도 최선을 다해 공부하고, 목표한 바를 이루고 싶다.
저녁에는 친구들과 모여서 이번 학기 계획에 대해 이야기를 나눴다. 다들 각자 다른 과목을 듣기 때문에 서로의 경험을 공유하는 것이 유익했다. 함께 공부할 스터디 그룹도 만들고, 과제를 함께 할 계획도 세웠다. 가을 학기는 시작부터 많은 준비가 필요하지만, 그만큼 보람 있는 결과를 얻을 수 있을 것 같다. 이번 학기에는 꼭 좋은 성과를 거두고 싶다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('maru26' || seq_diary_id.nextval, 'maru26', '인턴십 합격 소식', TO_DATE('2024-08-18', 'YYYY-MM-DD'), '오늘은 아침부터 좋은 소식을 들었다. 지난주에 지원한 인턴십에 합격했다는 메일을 받은 것이다. 메일을 확인하는 순간 기쁨과 안도감이 동시에 밀려왔다. 그동안 열심히 준비한 보람이 있었다는 생각이 들었다. 이번 인턴십은 내가 관심 있는 분야에서의 첫 실무 경험이 될 것이기 때문에 더욱 의미가 크다. 앞으로의 경험이 기대된다.
바로 가족들에게 소식을 전했고, 모두가 축하해 주었다. 특히 부모님께서는 내 노력을 인정해 주셔서 정말 기뻤다. 인턴십은 새로운 도전이자, 나의 커리어에 중요한 한 걸음이 될 것이다. 물론 긴장도 되지만, 그만큼 많은 것을 배우고 성장할 수 있을 것이라는 기대가 크다. 오늘 저녁은 이 기쁜 소식을 자축하기 위해 친구들과 함께 외식을 하기로 했다.
저녁에는 친구들과 만나서 인턴십 합격을 축하했다. 친구들도 나처럼 인턴십 준비를 하고 있어서, 서로의 경험을 공유하며 많은 이야기를 나눴다. 앞으로의 계획과 목표에 대해서도 이야기하며, 다 같이 힘을 내기로 했다. 이번 인턴십을 통해 내가 얼마나 성장할 수 있을지, 그리고 어떤 새로운 경험을 할 수 있을지 정말 기대된다. 앞으로의 도전을 두려워하지 않고, 자신감을 가지고 임해야겠다.');


INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '첫 번째 프로젝트 회의', TO_DATE('2024-08-03', 'YYYY-MM-DD'), '오늘은 이번 학기 첫 번째 프로젝트 회의가 있었다. 팀원들과 처음으로 만나서 프로젝트의 방향성과 목표에 대해 논의했다. 다들 서로 다른 아이디어를 가지고 있어서, 회의가 활발하게 진행되었다. 각자 맡을 역할도 정하고, 앞으로의 계획을 세웠다. 이번 프로젝트는 꽤 도전적인 주제라서 긴장되지만, 팀원들과 함께라면 잘 해낼 수 있을 것 같다.
회의가 끝난 후, 팀원들과 함께 저녁을 먹으며 더 많은 이야기를 나눴다. 서로의 강점과 관심사를 파악하면서, 어떻게 협력할지에 대해 고민했다. 팀워크가 중요한 프로젝트인 만큼, 서로를 잘 이해하고 배려하는 것이 중요하다는 생각이 들었다. 앞으로 몇 달 동안 이 팀과 함께할 생각에 기대가 된다. 팀원들 모두가 열정적이고 실력이 뛰어나서, 좋은 결과를 낼 수 있을 것 같다.
집에 돌아와 오늘 회의에서 논의한 내용을 정리하고, 개인적으로 해야 할 일들을 목록으로 작성했다. 프로젝트가 본격적으로 시작되기 전에 준비할 것들이 많아서, 체계적으로 계획을 세워야겠다. 이번 프로젝트는 나의 학문적 역량을 한층 더 발전시킬 수 있는 기회가 될 것이다. 지금은 조금 두렵기도 하지만, 팀원들과 함께라면 무엇이든 해낼 수 있을 것 같다. 앞으로의 과정이 기대된다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '카페에서의 만남', TO_DATE('2024-08-04', 'YYYY-MM-DD'), '오늘은 오랜만에 고등학교 친구와 카페에서 만났다. 서로 다른 대학에 진학한 이후로 자주 만나지 못했지만, 오늘은 오랜만에 시간을 맞춰서 만날 수 있었다. 친구는 여전히 밝고 긍정적인 에너지를 가지고 있어서, 만나는 순간부터 기분이 좋아졌다. 우리는 따뜻한 커피 한 잔을 마시며, 그동안의 이야기를 나눴다.
친구는 대학 생활에 잘 적응하고 있었고, 새로운 친구들도 많이 사귀었다고 했다. 나 역시 대학 생활의 다양한 경험들에 대해 이야기했다. 서로 다른 환경에서 공부하고 있지만, 비슷한 고민을 하고 있다는 것이 참 신기했다. 친구와의 대화는 항상 나에게 큰 힘이 된다. 우리는 서로의 고민을 나누고, 해결책을 찾기 위해 머리를 맞대기도 했다. 고등학교 시절의 추억을 떠올리며 웃기도 하고, 미래에 대한 이야기로 진지해지기도 했다.
시간이 어떻게 흘러갔는지도 모르게 대화를 나누다 보니 어느새 저녁이 되었다. 아쉬운 마음으로 헤어지며, 다음에 또 만나기로 약속했다. 친구와의 만남은 항상 나에게 큰 위로와 격려를 준다. 오늘도 덕분에 많은 에너지를 얻을 수 있었다. 집에 돌아오는 길에, 친구의 말들이 계속 떠올라 마음이 따뜻해졌다. 앞으로도 서로의 꿈을 응원하며 함께 성장하기로 했다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '새로운 취미 : 요가', TO_DATE('2024-08-05', 'YYYY-MM-DD'), '오늘은 처음으로 요가 수업에 참석했다. 요가를 시작하게 된 이유는 스트레스를 해소하고, 정신적인 안정을 찾기 위해서였다. 처음에는 몸이 굳어서 동작을 따라가기 힘들었지만, 강사님의 차분한 지도에 따라 천천히 동작을 익혀갔다. 요가는 단순한 운동이 아니라, 몸과 마음의 균형을 맞추는 데 큰 도움이 된다는 것을 느꼈다.
수업이 끝난 후에는 몸이 한결 가벼워지고 마음도 차분해졌다. 요가 매트 위에서의 시간이 얼마나 소중한지 새삼 깨달았다. 요가는 몸의 유연성뿐만 아니라, 내면의 평화와 집중력을 길러주는 데 큰 도움이 될 것 같다. 앞으로도 꾸준히 요가를 통해 자신을 돌보고, 스트레스를 해소할 수 있도록 해야겠다. 요가는 이제 나의 새로운 취미가 될 것 같다.
집에 돌아와 요가에 대한 책을 읽으며, 더 많은 정보를 얻기로 했다. 요가는 단순한 운동을 넘어, 나의 일상에 긍정적인 영향을 미칠 수 있는 중요한 요소가 될 수 있다는 생각이 들었다. 앞으로 꾸준히 요가를 하면서, 더 건강하고 균형 잡힌 삶을 살아가고 싶다. 오늘은 요가를 통해 새로운 에너지를 얻었고, 앞으로도 이 긍정적인 변화를 지속해 나가기를 기대한다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '새로운 목표 설정', TO_DATE('2024-08-06', 'YYYY-MM-DD'), '오늘은 새로운 목표를 설정하는 날로 정했다. 그동안 바쁘다는 핑계로 미루어 왔던 일들을 다시 정리하고, 앞으로의 계획을 세우기로 했다. 먼저, 이번 학기 동안 이루고 싶은 목표들을 목록으로 작성했다. 학업, 취미, 인간관계 등 다양한 분야에서 내가 이루고 싶은 것들을 구체적으로 정리했다. 목표를 시각화하니, 그동안 막연하게 생각했던 것들이 좀 더 명확해졌다.
목표를 설정하면서 중요한 것은 현실적인 계획을 세우는 것이다. 나는 각 목표에 대해 구체적인 실행 계획을 세우고, 이를 달성하기 위해 필요한 자원과 시간도 고려했다. 또한, 목표를 달성하기 위해 매일 실천해야 할 작은 행동들을 리스트로 작성했다. 이렇게 정리하고 나니, 목표 달성에 대한 자신감이 생겼다. 앞으로 꾸준히 이 계획을 따라가며, 작은 성과들을 쌓아 나가기로 다짐했다.
오늘 하루는 나 자신을 돌아보고, 앞으로의 방향성을 설정하는 의미 있는 시간이었다. 목표를 설정하고 나니, 마음이 한결 가벼워졌고, 더 열심히 노력하고 싶은 동기부여가 생겼다. 앞으로의 시간들을 헛되이 보내지 않도록, 매일 조금씩이라도 목표를 향해 나아가기로 했다. 오늘의 이 결심이 나의 미래를 더욱 밝게 만들어 줄 것이라 믿는다');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '동아리 회식', TO_DATE('2024-08-07', 'YYYY-MM-DD'), '오늘은 동아리 회식이 있었다. 이번 학기에 새로 들어온 신입 회원들과의 첫 회식이라서 모두가 기대에 찬 모습이었다. 회식 장소는 학교 근처의 한 식당이었고, 다 같이 모여 앉아 맛있는 음식을 먹으며 즐거운 시간을 보냈다. 신입 회원들이 처음이라 어색해할까 봐 선배들이 먼저 다가가서 대화를 이끌었다. 다행히 모두가 금방 친해져서 분위기가 화기애애했다.
회식 중간에는 각자 자기소개를 하고, 앞으로의 동아리 활동에 대한 의견도 나누었다. 신입 회원들의 새로운 아이디어가 참신해서, 앞으로의 활동이 기대되었다. 또한, 선배들과 후배들이 서로의 경험을 공유하며 더 깊이 이해할 수 있는 시간이 되었다. 동아리 활동은 단순한 취미를 넘어, 함께 성장하고 배울 수 있는 소중한 경험이라는 생각이 들었다.
회식이 끝나고 2차로 노래방에 갔다. 다 같이 신나게 노래를 부르며 스트레스를 풀었다. 특히 신입 회원들이 적극적으로 참여해 주어서 더욱 즐거운 시간이 되었다. 노래방에서의 시간은 정말 즐거웠고, 모두가 하나가 된 느낌이었다. 오늘 회식을 통해 동아리의 결속력이 더 강해진 것 같아서 기분이 좋았다. 앞으로도 이런 자리를 자주 만들어, 서로에게 힘이 되는 동아리 활동을 이어가고 싶다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '도서관에서의 하루', TO_DATE('2024-07-13', 'YYYY-MM-DD'), '오늘은 강의가 끝난 후 도서관에서 시간을 보냈다. 중간고사가 다가오면서 공부할 양이 점점 많아지고 있어서 조금씩 부담이 느껴진다. 특히 이번 학기에 듣는 전공 수업이 어려워서 이해하는 데 시간이 많이 걸린다. 그래도 오늘 도서관에서 몇 시간을 집중해서 공부하니까 그동안 이해하지 못했던 개념들이 조금씩 풀리는 느낌이었다. 교수님이 강조했던 부분들을 다시 한번 정리하고, 관련된 문제도 풀어봤다. 생각보다 잘 풀려서 기분이 좋았다.
공부를 마친 후에는 도서관 카페에서 잠시 쉬면서 커피 한 잔을 마셨다. 커피를 마시며 창밖을 바라보는데, 캠퍼스에 사람들이 북적이는 모습이 참 평화로워 보였다. 그런 순간이 너무 좋아서 조금 더 앉아있고 싶었지만, 저녁 약속이 있어서 친구들과 만나러 갔다. 캠퍼스 근처에 있는 작은 식당에서 함께 저녁을 먹었는데, 오랜만에 만난 친구들과 이야기를 나누며 스트레스를 풀 수 있었다. 친구들 덕분에 피로가 풀리는 것 같아 기분이 한결 가벼워졌다.
집에 돌아와서 오늘 하루를 돌아보니, 바쁜 와중에도 이렇게 여유를 찾을 수 있는 시간이 있다는 게 참 감사하다는 생각이 들었다. 내일도 해야 할 일이 많지만, 오늘처럼 차근차근 해나가면 잘 해낼 수 있을 것 같다. 이제 잠들기 전에 간단히 독서를 하고, 푹 쉬어야겠다. 내일은 조금 더 효율적으로 시간을 보내야지!');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '고향 친구들과의 재회', TO_DATE('2024-07-20', 'YYYY-MM-DD'), '오랜만에 고향 친구들과 연락이 닿았다. 대학교에 오고 나서 각자 바빠서 연락이 뜸했는데, 오늘은 갑자기 누가 먼저 연락을 해서 다 같이 단톡방에 모이게 되었다. 서로 지내는 이야기를 나누다 보니 시간 가는 줄 몰랐다. 친구들이 각자 새로운 환경에서 어떤 경험을 하고 있는지 듣는 게 참 재미있었다. 누군가는 해외로 교환학생을 준비하고 있고, 또 다른 친구는 자기가 정말 하고 싶었던 일을 찾았다고 한다.
나는 아직 진로에 대한 고민이 많아서 친구들의 이야기를 들으면서 자극도 받았고, 동시에 조금 불안해지기도 했다. 그래도 다들 열심히 자기 길을 찾아가고 있다는 게 너무 자랑스럽고, 나도 뒤처지지 않게 열심히 해야겠다고 다짐했다. 우리끼리 방학 때 다 같이 여행을 가기로 했는데, 벌써부터 기대가 된다. 여행을 가게 된다면 어디로 갈지, 무엇을 할지 상상만으로도 즐겁다.
대학에 오고 나서 새로운 친구들을 많이 사귀었지만, 고등학교 때의 친구들은 여전히 내 마음속에서 큰 자리를 차지하고 있다. 그때의 추억이 떠오르면서, 그 시절이 정말 소중했다는 걸 새삼 느꼈다. 우리는 서로를 잘 알고 이해해주기 때문에, 가끔은 그 시절로 돌아가고 싶다는 생각도 든다. 하지만 지금은 또 다른 새로운 경험을 쌓아가고 있으니, 현재에 충실하며 하루하루를 소중하게 보내야겠다고 마음먹었다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '동아리 프로젝트', TO_DATE('2024-07-24', 'YYYY-MM-DD'), '오늘은 동아리 모임이 있었다. 우리 동아리는 학교에서 진행하는 여러 프로젝트에 참여하는 활동을 하는데, 요즘 특히 큰 프로젝트를 준비하고 있어서 모두가 바쁘게 지내고 있다. 오늘 모임에서는 프로젝트 진행 상황을 점검하고, 앞으로 해야 할 일들을 분담했다. 다들 맡은 바를 열심히 하고 있어서, 이번 프로젝트가 성공적으로 마무리될 것 같은 느낌이 든다.
모임이 끝난 후, 동아리 사람들과 함께 늦은 저녁을 먹으러 갔다. 요즘 다들 바빠서 제대로 된 식사를 함께 하지 못했는데, 오늘은 다행히 시간이 맞아서 다 같이 갈 수 있었다. 저녁을 먹으면서 각자 프로젝트에 대한 생각을 나누기도 하고, 학교생활에 대한 이야기를 하기도 했다. 이 사람들이 있어서 대학 생활이 더 풍성해지고 재미있다는 생각이 들었다. 특히 같은 관심사를 가진 사람들과 함께하는 시간은 정말 소중하다.
저녁을 먹고 난 뒤에는 다 같이 근처 카페에 가서 커피를 마시며 이야기를 나눴다. 동아리 활동을 하면서 쌓인 우정과 신뢰가 있어서, 이들과 함께라면 어떤 어려움도 헤쳐나갈 수 있을 것 같은 느낌이 든다. 앞으로도 동아리 활동을 통해 많은 것을 배우고, 경험하고 싶다. 집에 돌아오는 길에, 오늘의 시간을 떠올리며 행복한 기분으로 잠들었다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '적응해가는 새 학기', TO_DATE('2024-08-09', 'YYYY-MM-DD'), '새로운 학기가 시작된 지도 벌써 한 달이 지났다. 처음엔 모든 게 낯설고 어려웠는데, 이제는 조금씩 적응해가고 있는 것 같다. 특히 새로운 과목들이 처음에는 이해하기 어려웠지만, 시간이 지나면서 교수님이 강조하시는 부분들을 중심으로 공부하니 조금씩 이해가 되고 있다. 오늘도 강의에서 몇 가지 새로운 개념을 배웠는데, 처음에는 어렵게 느껴졌지만 집에 돌아와 복습을 하면서 정리하니까 머릿속에 들어오는 것 같다.
교수님께서 수업 중에 질문을 하셨는데, 내심 대답하고 싶었지만 긴장해서 손을 들지 못했다. 나중에 쉬는 시간에 교수님을 찾아가 궁금했던 점을 여쭤봤다. 교수님께서는 내가 이해하지 못한 부분을 친절하게 설명해 주셨고, 덕분에 막혔던 부분이 시원하게 풀렸다. 이렇게 교수님께 직접 질문할 수 있는 용기를 낸 게 스스로 대견했다. 이제 남은 과제도 열심히 해야겠다고 다짐했다.
학기가 시작되기 전에는 이번 학기가 정말 힘들 것 같아서 걱정이 많았지만, 지금은 조금씩 자신감이 생기고 있다. 물론 여전히 해야 할 일이 많고, 그로 인해 가끔은 스트레스도 받지만, 이 모든 과정이 내가 성장하는 데 필요한 것들이라고 생각하니 마음이 한결 가벼워진다. 이번 학기가 끝날 때쯤엔 한층 더 성장한 나 자신을 발견할 수 있을 것 같아 기대가 된다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '비 오는 날의 산책', TO_DATE('2024-08-10', 'YYYY-MM-DD'), '오늘은 아침부터 비가 내려서 강의에 가기 싫었다. 요즘 계속 바빴기 때문에 피로가 쌓여 있어서 더 그랬던 것 같다. 그래도 스스로와의 약속을 지키기 위해 꾸역꾸역 일어나 강의를 들으러 갔다. 아침에 나올 때 우산을 챙기지 않아서 비를 맞으며 강의실에 도착했는데, 옷이 젖어서 하루가 더욱 지치게 느껴졌다.
그런데 강의를 듣다 보니 오늘 배운 내용이 생각보다 흥미로웠다. 교수님께서 예시를 통해 설명해주신 부분이 특히 기억에 남는다. 사실 아침에는 피곤하고 우울한 기분이었는데, 수업을 듣고 나니 기분이 조금 나아졌다. 점심시간이 되면서 비가 그쳐서, 친구들과 함께 캠퍼스를 산책하기로 했다. 비가 온 뒤의 상쾌한 공기를 마시며 걷는 게 생각보다 좋았다. 비가 내린 후의 캠퍼스는 평소보다 더욱 고요하고 운치가 있었다.
산책을 마치고 나서 오후 강의도 들었는데, 피곤함이 가시지 않았지만 집중하려고 노력했다. 수업이 끝나고 도서관에서 조금 더 공부를 하려고 했지만, 몸이 너무 피곤해서 일찍 집에 돌아왔다. 집에 돌아와 따뜻한 샤워를 하고 나니 하루의 피로가 조금은 풀리는 것 같았다. 오늘은 일찍 자고 내일은 좀 더 힘을 내야겠다. 이렇게 지친 날에도 배울 수 있는 것이 있다는 게 참 감사하다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '친구와의 요리 도전', TO_DATE('2024-08-13', 'YYYY-MM-DD'), '오늘은 친구와 함께 저녁을 만들었다. 둘 다 요리에 서툴러서 간단한 파스타를 만들기로 했다. 처음엔 실수도 많았지만, 요리를 하면서 서로 도와주는 과정이 재미있었다. 직접 만든 파스타는 생각보다 맛있었고, 함께한 시간이 소중하게 느껴졌다. 요리 후에는 집에서 오랜만에 친구와 수다를 떨며 즐거운 시간을 보냈다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '공원에서의 여유', TO_DATE('2024-08-17', 'YYYY-MM-DD'), '강의실에서 돌아오는 길에 캠퍼스 공원에 들렀다. 바람이 시원하고 햇볕이 따뜻해서 벤치에 앉아 한참을 쉬었다. 요즘 바쁜 일상 속에서 잠시 멈추고 여유를 찾는 시간이 필요한 것 같다. 오늘의 산책이 내 마음을 가라앉히고, 다시 시작할 힘을 주었다. 앞으로는 이런 시간을 더 자주 가져야겠다고 다짐했다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '도서관에서의 피로한 하루', TO_DATE('2024-08-19', 'YYYY-MM-DD'), '오늘은 정말 피곤한 하루였다. 강의도 많고, 과제도 쌓여 있어 밤늦게까지 도서관에서 공부했다. 그래도 해야 할 일들을 다 마쳤다는 성취감에 피곤함이 덜 느껴졌다. 이제 집에 가서 따뜻한 샤워를 하고 푹 쉬어야겠다. 오늘처럼 힘든 날도 있지만, 이런 날들이 쌓여 나를 더 강하게 만드는 것 같다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '음악 페스티벌에서의 즐거움', TO_DATE('2024-08-19', 'YYYY-MM-DD'), '오늘은 캠퍼스에서 작은 음악 페스티벌이 열렸다. 다양한 동아리의 공연을 친구들과 함께 즐기며 오랜만에 스트레스를 풀 수 있었다. 먹거리도 맛있었고, 노래도 좋았다. 이런 순간들이 대학 생활의 큰 즐거움이라는 생각이 들었다. 내일은 다시 바쁜 일상이지만, 오늘의 여유를 기억하며 잘 견뎌내야겠다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '아침 조깅의 상쾌함', TO_DATE('2024-08-19', 'YYYY-MM-DD'), '오늘은 아침 일찍 조깅을 했다. 오랜만에 몸을 움직이니 상쾌했다. 운동 후에 마시는 커피가 이렇게 맛있을 줄 몰랐다. 오늘 하루를 힘차게 시작할 수 있을 것 같다. 앞으로는 규칙적으로 운동을 하며 건강을 챙겨야겠다고 다짐했다. 몸이 건강해야 마음도 건강해진다는 걸 다시금 느꼈다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '교수님과의 면담', TO_DATE('2024-08-19', 'YYYY-MM-DD'), '오늘은 교수님과 진로에 대해 면담을 했다. 많은 고민이 있었는데, 교수님의 조언을 들으니 마음이 조금은 가벼워졌다. 앞으로의 방향에 대해 명확히 할 수 있었고, 그 목표를 향해 나아가야겠다고 다짐했다. 진로에 대한 고민은 많지만, 천천히 준비해 나가면 괜찮을 것 같다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '카페에서의 혼자만의 시간', TO_DATE('2024-08-19', 'YYYY-MM-DD'), '오늘은 혼자 카페에 앉아 시간을 보냈다. 책을 읽고, 에세이도 쓰면서 생각을 정리했다. 조용한 카페에서 나만의 시간을 갖는 게 참 좋았다. 바쁜 일상 속에서도 가끔은 혼자만의 시간을 갖는 것이 중요하다는 걸 느꼈다. 앞으로도 이런 시간을 더 자주 가져야겠다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '선배와의 커피 타임', TO_DATE('2024-08-19', 'YYYY-MM-DD'), '오늘은 동아리 선배와 만나 커피를 마셨다. 대학 생활과 진로에 대해 많은 이야기를 나눴다. 선배의 경험에서 얻은 조언이 큰 도움이 되었다. 나도 언젠가 후배들에게 좋은 영향을 줄 수 있는 사람이 되고 싶다. 오늘의 대화를 통해 많은 것을 배웠고, 앞으로 나아갈 방향이 조금은 명확해진 것 같다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '기말고사 후의 자유', TO_DATE('2024-08-19', 'YYYY-MM-DD'), '오늘 드디어 기말고사가 끝났다! 시험이 끝나니 마음이 홀가분해졌다. 친구들과 함께 늦은 밤까지 이야기를 나누며 스트레스를 풀었다. 방학 동안 무엇을 할지 계획을 세워야겠다는 생각이 들었다. 이제는 열심히 공부한 만큼 충분히 쉬고, 새로운 경험을 쌓을 시간이다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('minmonade' || seq_diary_id.nextval, 'minmonade', '인턴십 인터뷰', TO_DATE('2024-08-19', 'YYYY-MM-DD'), '오늘은 인턴십 인터뷰를 보고 왔다. 처음이라 많이 긴장했지만, 최선을 다했다. 결과가 어떻게 나올지 모르겠지만, 이번 경험을 통해 많은 것을 배웠다. 도전하는 것 자체가 나에게 큰 의미가 있다는 걸 느꼈다. 앞으로도 도전하는 자세를 잃지 말아야겠다. 결과를 기다리며 오늘의 경험을 소중히 간직하자.');



INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('mintwo20' || seq_diary_id.nextval, 'mintwo20', '새로운 프로그래밍 언어 학습', TO_DATE('2024-08-01', 'YYYY-MM-DD'), '오늘은 새로운 프로그래밍 언어인 Python을 공부하기 시작했다. Python은 문법이 간결하고 배우기 쉬워서 많은 사람들에게 인기가 있는 언어라고 들었다. 처음에는 기초적인 문법과 기본 함수들에 대해 학습했고, 간단한 프로젝트를 통해 실습해 보았다. 코딩을 하면서 느낀 것은 Python이 정말 직관적이고 유연하다는 것이었다. 앞으로 더 많은 기능을 익혀서 실제 프로젝트에 적용해 보고 싶다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('mintwo20' || seq_diary_id.nextval, 'mintwo20', '알고리즘 문제 해결', TO_DATE('2024-08-02', 'YYYY-MM-DD'), '오늘은 알고리즘 문제를 풀어보았다. 이번 문제는 다이나믹 프로그래밍 문제였는데, 처음에는 접근 방법을 이해하는 데 시간이 좀 걸렸다. 하지만 문제를 단계별로 나누어 해결하면서 조금씩 답을 찾아갔다. 문제를 해결하고 나니, 문제 해결 능력이 많이 향상된 것 같아 뿌듯했다. 알고리즘 문제를 풀면서 다양한 해결 방법을 배우고, 논리적 사고를 키우는 것이 중요하다는 것을 다시 한 번 느꼈다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('mintwo20' || seq_diary_id.nextval, 'mintwo20', '팀 프로젝트 시작', TO_DATE('2024-08-03', 'YYYY-MM-DD'), '오늘부터 팀 프로젝트가 시작되었다. 팀원들과 함께 만나서 프로젝트의 목표와 역할을 나누었다. 우리는 웹 애플리케이션을 개발하기로 했고, 각자 맡은 역할에 대해 논의했다. 역할을 분담하고 앞으로의 일정도 세우면서, 프로젝트를 성공적으로 진행하기 위한 계획을 세웠다. 처음에는 팀원들과의 협업이 조금 걱정되었지만, 다들 열정적이고 능력이 뛰어나서 좋은 결과를 기대하고 있다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('mintwo20' || seq_diary_id.nextval, 'mintwo20', '코딩 실습', TO_DATE('2024-08-04', 'YYYY-MM-DD'), '오늘은 실습 시간에 알고리즘 문제를 다시 풀어보았다. 이번에는 그래프 이론에 대한 문제였는데, 생각보다 복잡했다. 문제를 해결하기 위해 다양한 알고리즘을 시도해 보았고, 결국 문제를 해결하는 데 성공했다. 실습을 통해 알고리즘에 대한 이해도가 높아졌고, 문제를 푸는 과정에서 많은 것을 배울 수 있었다. 코딩 실습이 실제 문제 해결에 큰 도움이 된다는 것을 느꼈다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('mintwo20' || seq_diary_id.nextval, 'mintwo20', '기술 세미나 참석', TO_DATE('2024-08-05', 'YYYY-MM-DD'), '오늘은 기술 세미나에 참석했다. 세미나에서는 최신 기술 트렌드와 업계 동향에 대한 발표가 있었고, 여러 전문가들의 의견을 들을 수 있었다. 특히 인공지능과 머신러닝에 대한 부분이 인상적이었다. 세미나를 통해 최신 기술에 대한 정보를 얻을 수 있었고, 앞으로의 연구와 학습 방향에 대한 아이디어를 얻었다. 기술 세미나는 나의 지식과 시각을 넓히는 데 큰 도움이 되었다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('mintwo20' || seq_diary_id.nextval, 'mintwo20', '수업 준비와 복습', TO_DATE('2024-08-06', 'YYYY-MM-DD'), '오늘은 다가오는 수업을 준비하고 복습하는 시간을 가졌다. 지난 강의에서 배운 내용들을 다시 정리하고, 다음 수업에서 다룰 주제에 대해 미리 공부했다. 복습을 통해 내용이 더 잘 이해되었고, 수업에 대한 자신감이 생겼다. 앞으로도 꾸준히 복습하고 준비하는 습관을 가지면 학습 효과를 높일 수 있을 것 같다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('mintwo20' || seq_diary_id.nextval, 'mintwo20', '스터디 그룹 회의', TO_DATE('2024-08-07', 'YYYY-MM-DD'), '오늘은 스터디 그룹 회의가 있었다. 우리 그룹은 각자 맡은 주제에 대해 발표하고, 서로의 의견을 나누었다. 이번 회의에서는 데이터베이스 관련 주제에 대해 논의했는데, 다양한 시각에서 문제를 접근하는 방법에 대해 배울 수 있었다. 스터디 그룹의 토론은 나의 이해도를 높이는 데 큰 도움이 되고, 동기부여도 되었다. 앞으로도 적극적으로 참여하고 배운 내용을 실습해 보려고 한다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('mintwo20' || seq_diary_id.nextval, 'mintwo20', '해킹 대회 참가', TO_DATE('2024-08-08', 'YYYY-MM-DD'), '오늘은 해킹 대회에 참가했다. 팀원들과 함께 다양한 보안 문제를 해결하고, 제한된 시간 안에 최대한 많은 문제를 푸는 것을 목표로 했다. 대회가 진행되는 동안 긴장감과 흥미가 넘쳤고, 문제를 해결하면서 큰 성취감을 느꼈다. 해킹 대회를 통해 보안에 대한 실무적인 지식을 얻을 수 있었고, 팀워크와 문제 해결 능력도 향상된 것 같다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('mintwo20' || seq_diary_id.nextval, 'mintwo20', '자기 개발과 온라인 강좌', TO_DATE('2024-08-09', 'YYYY-MM-DD'), '오늘은 자기 개발을 위해 온라인 강좌를 수강했다. 강좌에서는 소프트웨어 개발의 모범 사례와 최신 기술 트렌드에 대한 내용이 포함되어 있었다. 강좌를 통해 새로운 기술에 대한 이해를 높일 수 있었고, 실제 업무에 어떻게 적용할 수 있을지에 대한 아이디어도 얻었다. 자기 개발은 계속해서 변화하는 기술 환경에 적응하는 데 필수적이라는 것을 느꼈다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('mintwo20' || seq_diary_id.nextval, 'mintwo20', '여름 방학 계획', TO_DATE('2024-08-10', 'YYYY-MM-DD'), '오늘은 여름 방학 동안의 계획을 세우는 시간을 가졌다. 방학 동안 하고 싶은 프로젝트와 학습 목표를 목록으로 작성하고, 이를 달성하기 위한 계획을 세웠다. 계획을 세우면서 구체적인 목표를 정하고, 필요한 자료와 리소스를 정리했다. 방학 동안 체계적으로 학습하고 성장할 수 있는 기회가 될 것 같아서 기대가 된다. 계획을 실천해 나가면서 많은 것을 배우고 싶다.');


INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('april0240' || seq_diary_id.nextval, 'april0240', '프로젝트 마감일', TO_DATE('2024-08-01', 'YYYY-MM-DD'), '오늘은 우리가 진행하던 프로젝트의 마감일이었다. 아침부터 팀원들과 마지막 점검을 진행하며, 각자의 작업을 점검하고 부족한 부분을 보완했다. 회의실에서 팀원들과 긴밀하게 협력하여 데이터를 정리하고, 보고서를 작성하는 데 집중했다. 마감일에 맞춰 모든 작업을 완료하고, 최종 보고서를 제출한 뒤에는 안도의 한숨을 내쉬었다.
프로젝트를 진행하는 동안 여러 가지 어려움이 있었지만, 팀원들과의 협력이 큰 도움이 되었다. 어려운 문제를 함께 해결하며 서로의 장점을 살릴 수 있었고, 결과적으로는 좋은 성과를 낼 수 있었다. 오늘은 저녁에 팀원들과 함께 간단한 회식을 하기로 했다. 프로젝트를 마무리하며 얻은 성취감을 나누고, 다음 프로젝트에 대한 계획을 논의하는 시간을 가지려고 한다. 이번 프로젝트는 나에게 많은 배움과 경험을 안겨주었고, 앞으로도 이런 도전적인 일들을 통해 더 성장하고 싶다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('april0240' || seq_diary_id.nextval, 'april0240', '회의에서의 논의', TO_DATE('2024-08-02', 'YYYY-MM-DD'), '오늘은 중요한 회의가 있었다. 회의에서는 새로운 마케팅 전략에 대한 논의가 이루어졌고, 팀원들과 함께 여러 가지 아이디어를 제안했다. 다양한 의견이 오갔고, 서로의 의견을 존중하며 논리적으로 토론하는 과정이 매우 유익했다. 각자의 아이디어가 어떻게 실질적으로 적용될 수 있을지에 대한 논의가 활발히 이루어졌고, 결과적으로 새로운 전략을 수립할 수 있었다.
회의가 끝난 후에는 팀원들과 잠깐의 자유시간을 가지며 오늘의 회의 내용에 대해 의견을 나누었다. 각자 맡은 역할에 대한 책임감을 느끼며, 앞으로의 실행 계획에 대해 다시 한 번 정리하는 시간을 가졌다. 오늘 논의된 내용은 향후 몇 주 동안 우리의 작업 방향에 큰 영향을 미칠 것이며, 이를 통해 회사의 성과를 더욱 높일 수 있을 것으로 기대된다. 이번 회의는 나에게 많은 통찰을 주었고, 팀워크의 중요성을 다시 한 번 느꼈다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('april0240' || seq_diary_id.nextval, 'april0240', '업무 자동화 도구 도입', TO_DATE('2024-08-03', 'YYYY-MM-DD'), '오늘은 회사에 새로운 업무 자동화 도구를 도입하는 작업이 있었다. 도구의 설치와 설정을 진행하고, 팀원들에게 사용법에 대한 교육을 실시했다. 처음에는 도구의 기능에 대해 익숙하지 않아 어려움을 겪었지만, 교육을 통해 기본적인 사용법을 익혔다. 이 도구는 반복적인 업무를 자동화해 주는 기능이 있어 업무 효율성을 크게 높일 수 있을 것으로 기대된다.
도구를 사용해 본 결과, 반복적인 작업들이 빠르게 처리되었고, 팀원들이 더 창의적인 업무에 집중할 수 있게 되었다. 오늘은 도구의 도입 과정에서 발생한 문제를 해결하기 위해 추가적인 설정과 조정 작업을 진행했다. 앞으로 이 도구를 적극적으로 활용하여 업무의 생산성을 높이고, 팀의 목표를 더 효과적으로 달성할 수 있도록 해야겠다. 새로운 도구의 도입은 회사에 긍정적인 변화를 가져올 것이라고 확신한다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('april0240' || seq_diary_id.nextval, 'april0240', '업무 효율성 회의', TO_DATE('2024-08-04', 'YYYY-MM-DD'), '오늘은 업무 효율성을 높이기 위한 회의가 있었다. 회의에서는 각 부서의 업무 프로세스를 점검하고, 비효율적인 부분을 개선하기 위한 방법을 논의했다. 다양한 아이디어와 제안이 있었고, 이를 바탕으로 새로운 업무 프로세스를 설계하기로 결정했다. 회의 중에는 각 부서의 의견을 수렴하고, 실질적인 개선 방안을 도출하기 위해 집중했다.
회의가 끝난 후에는 개선된 프로세스에 대해 팀원들과 다시 한 번 검토하는 시간을 가졌다. 새로운 프로세스가 실제로 업무에 어떻게 적용될 수 있을지에 대한 시뮬레이션을 진행했으며, 각 부서의 피드백을 반영하여 최종안을 마련했다. 업무 효율성을 높이는 과정에서 팀원들의 적극적인 참여와 협력이 큰 도움이 되었다. 앞으로 새로운 프로세스를 적용해 보면서 업무의 개선점을 지속적으로 모니터링하고, 필요한 부분은 계속해서 수정해 나가야겠다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('april0240' || seq_diary_id.nextval, 'april0240', '부서 간 협업 회의', TO_DATE('2024-08-05', 'YYYY-MM-DD'), '오늘은 부서 간 협업을 위한 회의가 진행되었다. 서로 다른 부서의 업무가 어떻게 협력하여 시너지를 낼 수 있을지를 논의하는 자리였다. 회의에서는 각 부서의 역할과 책임을 명확히 하고, 협업의 목표를 설정했다. 다양한 부서의 의견을 듣고 조율하는 과정이 필요했지만, 모든 부서가 공통의 목표를 가지고 협력하기로 했다.
회의가 끝난 후에는 각 부서 간의 협업을 원활히 하기 위한 세부 계획을 수립했다. 필요한 자원과 인력, 그리고 일정 등을 조정하여 협업이 효과적으로 이루어질 수 있도록 했다. 부서 간 협업은 회사의 전반적인 성과를 높이는 데 중요한 요소라고 생각하며, 이번 협업을 통해 더 나은 결과를 얻을 수 있을 것이라고 믿는다. 앞으로 협업의 진행 상황을 면밀히 모니터링하며, 필요한 지원을 아끼지 않겠다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('april0240' || seq_diary_id.nextval, 'april0240', '상사와의 1:1 면담', TO_DATE('2024-08-06', 'YYYY-MM-DD'), '오늘은 상사와 1:1 면담을 진행했다. 면담에서는 최근의 업무 성과와 앞으로의 목표에 대해 논의했다. 상사로부터 받은 피드백을 바탕으로 나의 강점과 개선할 점에 대해 깊이 이해할 수 있었다. 특히, 앞으로 더 집중해야 할 부분과 향후 경력 개발 방향에 대한 조언이 유익했다.
면담이 끝난 후, 받은 피드백을 바탕으로 개인적인 계획을 세우고, 더 나은 성과를 내기 위한 구체적인 액션 아이템을 정리했다. 상사의 피드백은 나의 업무 능력을 향상시키는 데 중요한 역할을 하고 있으며, 앞으로도 지속적으로 소통하고 발전해 나가야겠다는 다짐을 했다. 면담을 통해 얻은 인사이트를 적극적으로 활용하여, 더욱 효율적이고 능률적인 업무 수행을 목표로 하겠다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('april0240' || seq_diary_id.nextval, 'april0240', '팀워크 강화 워크숍', TO_DATE('2024-08-07', 'YYYY-MM-DD'), '오늘은 팀워크를 강화하기 위한 워크숍에 참여했다. 워크숍에서는 팀원 간의 소통을 개선하고, 협력적인 업무 환경을 조성하기 위한 다양한 활동이 진행되었다. 여러 가지 팀 빌딩 게임과 문제 해결 활동을 통해 팀원들 간의 유대감을 쌓았고, 서로의 강점을 파악할 수 있는 기회를 가졌다.
워크숍의 마지막에는 팀워크를 강화하기 위한 실질적인 전략과 실행 계획을 세웠다. 이러한 활동은 팀의 결속력을 높이는 데 큰 도움이 되었고, 앞으로의 업무에서 협력과 소통을 더욱 중요시해야겠다는 생각을 하게 되었다. 오늘의 워크숍을 통해 팀워크의 중요성을 다시 한 번 느꼈으며, 이를 통해 더욱 강력한 팀을 만들 수 있을 것이라고 믿는다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('april0240' || seq_diary_id.nextval, 'april0240', '고객과의 미팅', TO_DATE('2024-08-08', 'YYYY-MM-DD'), '오늘은 주요 고객과의 미팅이 있었다. 고객의 요구사항과 기대를 정확히 이해하고, 우리가 제공할 수 있는 솔루션에 대해 논의했다. 미팅 중에는 고객의 피드백을 적극적으로 수렴하며, 그들의 요구에 맞춰 제안서를 조정하는 작업을 진행했다. 고객의 기대를 충족시키기 위해 필요한 자원과 인력을 파악하고, 실질적인 해결 방안을 모색했다.
미팅이 끝난 후에는 고객과의 관계를 강화하기 위한 후속 작업을 정리했다. 고객의 요구를 정확히 반영하기 위해 팀 내에서 추가적인 조율이 필요할 것으로 보인다. 고객과의 원활한 소통과 피드백 반영은 우리 서비스의 품질을 높이는 데 중요한 요소라고 생각하며, 이를 통해 신뢰를 얻고 장기적인 파트너십을 구축할 수 있도록 노력하겠다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('april0240' || seq_diary_id.nextval, 'april0240', '업무 리뷰와 반성', TO_DATE('2024-08-09', 'YYYY-MM-DD'), '오늘은 최근의 업무 성과를 리뷰하고 반성하는 시간을 가졌다. 각 업무의 결과를 분석하고, 성공적인 부분과 개선이 필요한 부분을 점검했다. 업무 중에 발생한 문제와 그에 대한 해결 방안을 정리하고, 다음에는 어떻게 더 나은 결과를 낼 수 있을지에 대해 고민했다.
리뷰 후에는 앞으로의 업무 계획을 세우고, 달성 가능한 목표를 설정했다. 개인적으로는 더 나은 업무 성과를 내기 위해 무엇을 개선해야 할지에 대한 인사이트를 얻었고, 이를 바탕으로 실질적인 개선 방안을 도출해 냈다. 업무 리뷰는 나의 업무 능력을 향상시키는 데 중요한 과정이라고 생각하며, 앞으로도 정기적으로 리뷰를 진행하여 더 나은 성과를 추구할 것이다.');

INSERT INTO user_diary (diary_id, user_id, diary_title, write_date, diary_content)
VALUES ('april0240' || seq_diary_id.nextval, 'april0240', '직무 교육 참여', TO_DATE('2024-08-10', 'YYYY-MM-DD'), '오늘은 직무 교육에 참여했다. 교육에서는 최신 업무 도구와 기술, 그리고 업무 효율성을 높이는 방법에 대한 내용을 다루었다. 새로운 도구와 기술에 대한 교육을 받으며, 이를 업무에 어떻게 적용할 수 있을지에 대한 아이디어를 얻었다. 교육을 통해 많은 것을 배울 수 있었고, 앞으로의 업무에 큰 도움이 될 것으로 기대된다.
교육 후에는 배운 내용을 팀원들과 공유하고, 실제 업무에 적용할 수 있는 방안을 논의했다. 직무 교육은 최신 정보를 얻고, 업무 능력을 향상시키는 데 필수적이라고 생각하며, 앞으로도 계속해서 교육에 참여하여 지속적으로 발전해 나가고 싶다. 오늘의 교육을 통해 얻은 지식을 업무에 적극적으로 활용하여, 더 나은 결과를 만들어 나가겠다.');


-- FRIENDSHIPS 테이블 생성: 사용자의 친구 관계 정보를 저장
CREATE TABLE friendships (
    registration_no NUMBER,
    user_id VARCHAR2(50),                  -- 사용자 ID
    friend_id VARCHAR2(50),                -- 친구 ID
    registration_date DATE DEFAULT SYSDATE
);

-- REQUESTFRIEND 테이블 생성: 친구 신청 정보를 저장
CREATE TABLE requestfriend (
    registration_no NUMBER,
    user_id VARCHAR2(50),                  -- 친구 신청을 받은 사람
    friend_id VARCHAR2(50),                -- 친구 신청을 한 사람
    registration_date DATE DEFAULT SYSDATE
);

-- FRIENDSHIPS 테이블에 데이터 삽입: 샘플 친구 관계 추가
INSERT INTO friendships (registration_no, user_id, friend_id)
VALUES (NVL((SELECT MAX(registration_no) FROM friendships), 0) + 1, 'jpt26', 'maru26');

INSERT INTO friendships (registration_no, user_id, friend_id)
VALUES (NVL((SELECT MAX(registration_no) FROM friendships), 0) + 1, 'jpt26', 'minmonade');

INSERT INTO friendships (registration_no, user_id, friend_id)
VALUES (NVL((SELECT MAX(registration_no) FROM friendships), 0) + 1, 'jpt26', 'mintwo20');

INSERT INTO friendships (registration_no, user_id, friend_id)
VALUES (NVL((SELECT MAX(registration_no) FROM friendships), 0) + 1, 'jpt26', 'april0240');

INSERT INTO friendships (registration_no, user_id, friend_id)
VALUES (NVL((SELECT MAX(registration_no) FROM friendships), 0) + 1, 'maru26', 'jpt26');

INSERT INTO friendships (registration_no, user_id, friend_id)
VALUES (NVL((SELECT MAX(registration_no) FROM friendships), 0) + 1, 'minmonade', 'jpt26');

INSERT INTO friendships (registration_no, user_id, friend_id)
VALUES (NVL((SELECT MAX(registration_no) FROM friendships), 0) + 1, 'mintwo20', 'jpt26');

INSERT INTO friendships (registration_no, user_id, friend_id)
VALUES (NVL((SELECT MAX(registration_no) FROM friendships), 0) + 1, 'april0240', 'jpt26');

-- ACCOUNTBOOK 테이블 생성: 사용자의 가계부 정보를 저장
CREATE TABLE accountbook (
    account_book_id NUMBER PRIMARY KEY,    -- 가계부 ID, 기본 키
    user_id VARCHAR2(30),                  -- 사용자 ID
    account_date VARCHAR2(20),             -- 가계부 작성 날짜 (문자열)
    salary NUMBER,                         -- 급여
    side_job NUMBER,                       -- 부업 수입
    saving NUMBER,                         -- 저축
    income_total NUMBER,                   -- 총 수입
    food_expenses NUMBER,                  -- 식비
    traffic NUMBER,                        -- 교통비
    culture NUMBER,                        -- 문화비
    clothing NUMBER,                       -- 의류비
    beauty NUMBER,                         -- 미용비
    telecom NUMBER,                        -- 통신비
    membership_fee NUMBER,                 -- 회비
    daily_necessity NUMBER,                -- 생필품 비용
    occasions NUMBER,                      -- 경조사비
    spending_total NUMBER,                 -- 총 지출
    income_spending_total NUMBER           -- 총 수입 - 총 지출 (잔액)
);

-- ACCOUNTBOOK 테이블에 데이터 삽입: 샘플 가계부 데이터 추가
INSERT INTO accountbook (
    account_book_id, user_id, account_date, salary, side_job, saving, income_total,
    food_expenses, traffic, culture, clothing, beauty, telecom, membership_fee,
    daily_necessity, occasions, spending_total, income_spending_total
)
VALUES (
    1, 'jpt26', '2024-08-01', 3000, 500, 200, 3700,
    300, 100, 150, 200, 50, 60, 20,
    30, 50, 960, 2740
);

-- ACCOUNTBOOK 테이블의 모든 데이터를 조회
-- SELECT * FROM accountbook;

-- TODOLIST 테이블 생성: 사용자의 할 일 목록을 저장
CREATE TABLE todolist (
    todolist_id NUMBER PRIMARY KEY,         -- 할 일 ID, 기본 키
    user_id VARCHAR2(20),                   -- 사용자 ID
    todolist_contents VARCHAR2(200),        -- 할 일 내용
    todolist_status VARCHAR2(10)            -- 할 일 상태 (등록, 완료 등)
);

-- TODOLIST 테이블에 데이터 삽입: 샘플 할 일 목록 추가
INSERT INTO todolist (todolist_id, user_id, todolist_contents, todolist_status)
VALUES (1, 'jpt26', '장보기', 'reg');

INSERT INTO todolist (todolist_id, user_id, todolist_contents, todolist_status)
VALUES (2, 'jpt26', '일기 쓰기', 'reg');

INSERT INTO todolist (todolist_id, user_id, todolist_contents, todolist_status)
VALUES (3, 'jpt26', '증명사진 찍기', 'reg');

INSERT INTO todolist (todolist_id, user_id, todolist_contents, todolist_status)
VALUES (4, 'jpt26', '동사무소 가기', 'cmp');

INSERT INTO todolist (todolist_id, user_id, todolist_contents, todolist_status)
VALUES (5, 'jpt26', '친구랑 밥 먹고 카페가기', 'reg');

-- TODOLIST 테이블의 모든 데이터를 조회
-- SELECT * FROM todolist;

-- TIMECAPSULE 테이블 생성: 사용자의 타임캡슐 정보를 저장
CREATE TABLE timecapsule (
    tc_id NUMBER PRIMARY KEY,             -- 타임캡슐 ID, 기본 키
    tc_user_id VARCHAR2(30),              -- 사용자 ID
    tc_date VARCHAR2(20),                 -- 날짜 (문자열 형태)
    tc_content VARCHAR2(500)              -- 타임캡슐 내용 (최대 500자)
);

-- FILE_INFO 테이블 생성: 파일 정보 저장
CREATE TABLE file_info (
    file_id NUMBER PRIMARY KEY,           -- 파일 ID, 기본 키
    user_id VARCHAR2(255) NOT NULL,       -- 사용자 ID (필수)
    file_name VARCHAR2(255) UNIQUE NOT NULL,  -- 파일 이름 (유니크, 필수)
    origin_file_name VARCHAR2(255),       -- 원본 파일 이름
    file_path VARCHAR2(500),              -- 파일 경로
    url_file_path VARCHAR2(500) NOT NULL  -- 파일 URL 경로 (필수)
);

-- 챗봇의 질문 및 대답 1차 데이터 저장할 테이블
CREATE TABLE talk_to_bot_all (
    save_date DATE DEFAULT SYSDATE,
    user_id VARCHAR2(100) NOT NULL,
    room_index NUMBER NOT NULL,
    data_id VARCHAR2(1000) NOT NULL,
    question_html VARCHAR2(4000) NOT NULL,
    chat_html VARCHAR2(4000)
);

-- 대화창 불용어 요소 키워드 제거 테이블
CREATE TABLE EXCLUDED_KEYWORDS (
    user_id VARCHAR2(1000) NOT NULL,
    data_id VARCHAR2(100) NOT NULL,
    data_index VARCHAR2(100) NOT NULL,
    message_index NUMBER,
    save_date DATE DEFAULT SYSDATE,
    excluded_keyword VARCHAR2(100) NOT NULL
);

-- 일정 테이블 생성
CREATE TABLE calender (
    calender_date DATE DEFAULT SYSDATE,
    data_id VARCHAR2(100) NOT NULL,
    user_id VARCHAR2(100) NOT NULL,
    calender_title VARCHAR2(100) NOT NULL,
    has_friends CHAR(1) NOT NULL
);

CREATE TABLE calender_friends (
    cfno NUMBER PRIMARY KEY,
    registration_date DATE DEFAULT SYSDATE,
    data_id VARCHAR2(100) NOT NULL,
    friend_id VARCHAR2(100) NOT NULL,
    friend_name VARCHAR2(100) NOT NULL 
);

CREATE TABLE calender_memo_diary (
    registration_date DATE DEFAULT SYSDATE,
    data_id VARCHAR2(100) NOT NULL,
    reader_id VARCHAR2(100) NOT NULL,
    appointment_time VARCHAR2(100),
    memo_content VARCHAR2(4000),
    diary_title VARCHAR2(1000),
    diary_content CLOB
);



---- 테이블 삭제 (순서 주의)
--DROP TABLE calender_memo_diary;
--DROP TABLE calender_friends;
--DROP TABLE calender;
--DROP TABLE EXCLUDED_KEYWORDS;
--DROP TABLE talk_to_bot_all;
--DROP TABLE file_info;
--DROP TABLE timecapsule;
--DROP TABLE todolist;
--DROP TABLE accountbook;
--DROP TABLE requestfriend;
--DROP TABLE friendships;
--DROP TABLE user_diary;
--DROP TABLE users;
----
------ 시퀀스 삭제
--DROP SEQUENCE seq_diary_id;
--
---- 모든 변경 사항 커밋
COMMIT;

