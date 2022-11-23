#include <iostream>
#include <random>
#include <stdexcept>

using namespace std;
#define LEN	3


class BaseballGame
{
public:
	BaseballGame()
	{
		cout << "숫자 야구 게임을 시작합니다.\n";
		init();
	}

	~BaseballGame() {}

	void Play()
	{
		while (true)
		{
			input();
			if (scoring() == LEN)
				break;
		}

		cout << "3개의 숫자를 모두 맞히셨습니다! 게임 종료\n";
	}


private:
	void init()
	{
		setRandomAns();
	}

	void input()
	{
		cout << "숫자를 입력해주세요 : ";
		cin >> mNums;

		try
		{
			if (mNums.length() != LEN)
				throw invalid_argument("1부터 9까지의 서로 다른 3자리 수가 아닙니다.\n");
			for (size_t i = 0; i < LEN; i++)
			{
				if (mNums[i] < '1' || mNums[i] > '9')
					throw invalid_argument("1부터 9까지의 서로 다른 3자리 수가 아닙니다.\n");

				for (size_t j = 0; j < LEN; j++)
				{
					if (i != j && mNums[i] == mNums[j])
						throw invalid_argument("1부터 9까지의 서로 다른 3자리 수가 아닙니다.\n");
				}
			}

		}
		catch (const invalid_argument& e)
		{
			cerr << e.what();
			cerr << typeid(e).name() << endl;
			abort();
		}
	}

	int scoring()
	{
		int ball = 0,
			strike = 0;

		for (size_t i = 0; i < LEN; i++)
		{
			for (size_t j = 0; j < LEN; j++)
			{
				if (mNums[i] == mAns[j])
				{
					if (i == j)
						strike++;
					else
						ball++;
				}
			}
		}

		printScore(ball, strike);
		return strike;
	}

	void printScore(int ball, int strike)
	{
		if (ball == 0)
		{
			if (strike == 0)
				cout << "낫싱\n";
			else
				cout << strike << "스트라이크\n";
		}
		else if (strike == 0)
			cout << ball << "볼\n";
		else
			cout << ball << "볼 " << strike << "스트라이크\n";
	}

	void setRandomAns()
	{
		random_device						rd;
		mt19937								gen(rd());
		uniform_int_distribution<size_t>	dis(1, 9);

		char	randomNum;
		size_t	idx = 0;
		while (idx < LEN)
		{
			randomNum = char(dis(gen) + '0');

			bool dup = false;
			for (size_t i = 0; i < idx; i++)
			{
				if (randomNum == mAns[i])
				{
					dup = true;
					break;
				}
			}

			if (!dup)
				mAns[idx++] = randomNum;
		}
	}


private:
	string	mNums;
	char	mAns[LEN + 1];

};


int main()
{
	BaseballGame game;
	game.Play();

	return 0;
}