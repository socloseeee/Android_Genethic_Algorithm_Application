def main(data):

    from os.path import dirname, join
    from copy import deepcopy
    from random import choice as c, randint as r
    from colorama import Fore, init, Style, Back
    from tqdm import tqdm
    import json

    init(autoreset=True)

    # Генерируем матрицу заданий
    def generate_matrix(m, n, T1, T2):
        return [[r(T1, T2) for j in range(n)] for i in range(m)]

    # Генерируем особь (рандомно/детерминированно):
    def generate_individ(m, n, random, bnds = None):
        individ = []
        if random:
            return [r(1, 255) for _ in range(m)]
        else:
            for row in m:
                for i, elem in enumerate(row):
                    if '\x1b[31m' in str(elem):  # '\x1b[31m' - идентификатор цвета
                        if bnds == 0:
                            individ.append(i * (255 // n) + (255 // n) // 2)  # чётко по центру между двумя границами в процессоре
                        elif bnds == 1:
                            individ.append(i * (255 // n))  # чётко по левой границе в процессоре
                        elif bnds == 2:
                            individ.append(i * (255 // n) + (255 // n))  # чётко по правой границе в процессоре
                        elif bnds == 3:
                            individ.append(r(i * (255 // n) + 1, i * (255 // n) + (255 // n)))  # рандомно между двумя границами в процессоре
        return individ

    # Считаем загрузки:
    def count_load(individ, n, m, tasks, t=255):
        load_result = [0 for _ in range(n)]
        proc = [i for i in range(t//n, t + t//n, int(t//n))]
        for j, gen in enumerate(individ):
            for i in range(n):
                if gen <= proc[i]:
                    load_result[i] += tasks[j][i]
                    break
        return load_result

    # Считаем лучшую загрузку для каждой особи:
    def best_load(max_loads):
        result = [max(el) for el in max_loads]
        return (min(result), result.index(min(result)))

    # Двухточечный кроссовер
    def crossover(parent1, parent2):
        T1 = r(1, len(parent1) - 1)
        T2 = r(1, len(parent1) - 1)
        while T2 == T1:
            T2 = r(1, len(parent1) - 1)
        if T1 > T2:
            T1, T2 = T2, T1
        #print(f'parents({T1},{T2}):\n{parent1}\n{parent2}')
        child1, child2 = parent1[:T1] + parent2[T1:T2+1] + parent1[T2+1:], parent2[:T1] + parent1[T1:T2+1] + parent2[T2+1:],
        #print(f'children:\n{child1}\n{child2}')
        return child1, child2

    # Двухточечная мутация
    def mutation(child, Pm):
        child_copy = deepcopy(child)
        child_genes = [e for e in child_copy]
        gen = c(child_genes)
        while r(1, 100) < Pm:
            gen = c(child_genes)
        that_gen = deepcopy(gen)
        binary_gen = '00000000'
        for j in range(len(binary_gen)):
            binary_gen = binary_gen[:j] + str(gen % 2) + binary_gen[j + 1:]
            gen //= 2
        binary_gen = binary_gen[::-1]
        binary_gen = list(binary_gen)
        index1 = r(0, len(binary_gen)-1)
        index2 = r(0, len(binary_gen)-1)
        if binary_gen.count(binary_gen[0]) != len(binary_gen):
            while index2 == index1 or binary_gen[index1] == binary_gen[index2]: # чтоб 0 менялся с 1 и индексы не одинаковые
                index2 = r(0, len(binary_gen)-1)
        binary_gen[index1], binary_gen[index2] = binary_gen[index2], binary_gen[index1]
        binary_gen = "".join(binary_gen)
        child_copy = [genes if genes != that_gen else int(binary_gen, 2) for genes in child_copy]
        return child_copy




    #print(data)
    data = json.loads(data, strict=False)
    m, n, T1, T2, z, k, Pk, Pm = int(data['m']), int(data['n']), int(data['T1']), int(data['T2']), int(data['z']), int(data['k']), int(data['Pk']), int(data['Pm'])
    matrix = [[int(el) for el in elem[:-3].split(', ')] for elem in data['Matrix'][2:].split('[')]
    repeat = int(data['repeat'])
    is_create_way_dict = {
        "Конкретный метод": 0,
        "Все методы": 1
    }
    is_create_way = is_create_way_dict[data['isCreateWay']]
    create_way_dict = {
        "100% Рандомно": 0,
        "50% Рандомно + 50% детерминированно": 1,
        "25% Рандомно + 75% детерминированно": 2,
        "75% Рандомно + 25% детерминированно": 3,
        "50% Плотников-Зверев + 50% Барьер": 4
    }
    create_way = create_way_dict[data['createWay']] if is_create_way != 1 else None
    bounds_dict = {
        "По центру": 0,
        "Слева": 1,
        "Справа": 2,
        "Рандомно": 3,
    }
    bounds = bounds_dict[data['bounds']]

    # Генерация массива и сортировка
    # n = 5    # кол-во процессоров
    # T1 = 20  # левая граница задания
    # T2 = 30  # правая граница задания
    # m = 10   # кол-во заданий
    # z = 100   # кол-во особей
    # k = 30   # кол-во поколений подряд при котором лучшая загрузка будет повторяться k-раз
    # Pk = 99  # вероятность кроссовера
    # Pm = 99  # вероятность мутации
    #
    # # Вводим данные для исследования
    # while True:
    #     chose = input('Использовать готовую матрицу или сгенерировать новую | Use a ready-made matrix or generate a new one? (1/0) > ')
    #     if chose == '0':
    #         matrix = generate_matrix(m, n, T1, T2)
    #         matrix_sum = sorted([sum(elem) for elem in matrix], reverse=True)
    #         matrix = sorted(matrix, key=lambda x: sum(x), reverse=True)
    #         matrix_file = open('matrix.txt', 'w', encoding='utf-8')
    #         [matrix_file.write(f"{elem}\n") for elem in matrix]
    #         matrix_file.close()
    #         break
    #     elif chose == '1':
    #         matrix_file = open('matrix.txt', 'r', encoding='utf-8')
    #         matrix = []
    #         data = matrix_file.readlines()
    #         [matrix.append([int(el) for el in elem[1:-2].split(', ')]) for elem in data]
    #         # print(matrix)
    #         matrix_file.close()
    #         break
    #     else:
    #         print('Некорректный ввод!')
    # repeat = int(
    #     input(
    #         Fore.MAGENTA + "Number of repetitions of GA cycles | Количество повторов цикла ГА > " + Style.RESET_ALL
    #     )
    # )
    # is_create_way = input(
    #     "Select all or a specific method of forming (partitioning) the initial generation | "
    #     "Выбрать все или конкретный метод формирования (разбиения) начального поколения(1/any) > "
    # )
    # is_create_way = int(is_create_way) if is_create_way.isdigit() else is_create_way
    # if is_create_way != 1:
    #     create_way = int(
    #         input(
    #             "Way of forming the initial generation | Способ формирования начального поколения:\n"
    #             "100% random species | 100% рандомных особей(0)\n"
    #             "50% random + 50% determinate species | 50% рандомно + 50% детерминированных особей(1)\n"
    #             "25% random + 75% determinate species | 25% рандомно + 75% детерминированных особей(2)\n"
    #             "75% random + 25% determinate species | 75% рандомно + 25% детерминированных особей(3)\n"
    #             "50% Plt-Zvr + 50% barrier species | 50% Плт-Зврв + 50% барьерных особей(4)\n"
    #             "> "
    #         )
    #     )
    # bounds = int(
    #     input(
    #         Fore.YELLOW +
    #         "Ways to form genes | Способы формирования генов:\n"
    #         "Clearly centered between two borders in the processor | Чётко по центру между двумя границами в процессоре(0)\n"
    #         "Clearly on the left border in the processor | Чётко по левой границе в процессоре(1)\n"
    #         "Clearly on the right border in the processor | Чётко по правой границе в процессоре(2)\n"
    #         "Randomly between two boundaries in the processor | Рандомно между двумя границами в процессоре(3)\n"
    #         "> "
    #         + Style.RESET_ALL
    #     )
    # )

    # Метод минимальных элементов
    new_matrix, result = [], [0] * n
    for j in range(m):
        new_matrix.append([])
        check = 0   # check - позволяет вычленять из двух одинаковых только первое левое значение
        for i in range(n):
            if matrix[j][i] != min(matrix[j]) or matrix[j][i] == check:
                new_matrix[j].append(matrix[j][i])
            else:
                result[i] += matrix[j][i]
                new_matrix[j].append(Fore.RED + str(matrix[j][i]) + Style.RESET_ALL)
                check = int(matrix[j][i])
    min_elem_method = deepcopy(new_matrix)
    # Метод Плотникова-Зверева
    result_str = [0] * n
    new_matrix2 = []
    plotnikov_zverev_method = deepcopy(matrix)
    for j in range(m):
        for i in range(n):
            result_str[i] += matrix[j][i]
        min_index = result_str.index(min(result_str))
        for i in range(n):
            if i != min_index:
                result_str[i] -= matrix[j][i]
        plotnikov_zverev_method[j][min_index] = Fore.RED + str(matrix[j][min_index]) + Style.RESET_ALL
    # Метод квадратов
    result_str1 = [0] * n
    new_matrix = [[0 for i in range(n)] for j in range(m)]
    square_method = deepcopy(matrix)
    for j in range(m):
        min_sum = [0] * n
        for i in range(n):
            result_str1[i] += matrix[j][i]
            min_sum[i] = sum([elem * elem for elem in result_str1])
            result_str1[i] -= matrix[j][i]
        min_sum_index = min_sum.index(min(min_sum))
        for i in range(n):
            if i == min_sum_index:
                result_str1[i] += matrix[j][i]
        square_method[j][min_sum_index] = Fore.RED + str(matrix[j][min_sum_index]) + Style.RESET_ALL
    # Метод барьера
    barrier = sum(result) / n
    result_str2 = [0] * n
    barrier_method = []
    flag = False
    for j in range(m):
        if not flag:
            barrier_method.append([])
            check = 0  # check - позволяет вычленять из двух одинаковых только первое левое значение
            for i in range(n):
                if matrix[j][i] != min(matrix[j]) or matrix[j][i] == check:
                    barrier_method[j].append(str(matrix[j][i]))
                else:
                    result_str2[i] += matrix[j][i]
                    barrier_method[j].append(Fore.RED + str(matrix[j][i]) + Style.RESET_ALL)
                    check = int(matrix[j][i])
                    if result_str2[i] > barrier and not flag:
                        flag = True
                        barrier_method.append([])
                        for s in range(n):
                            barrier_method[-1].append('- ')
        else:
            for i in range(n):
                result_str2[i] += matrix[j][i]
            min_index = result_str2.index(min(result_str2))
            for i in range(n):
                if i != min_index:
                    result_str2[i] -= matrix[j][i]
            barrier_method.append([Fore.RED + str(matrix[j][i]).ljust(2) + Style.RESET_ALL if min_index == i else str(matrix[j][i]).ljust(2) for i in range(len(result_str2))])

    methods = [min_elem_method, plotnikov_zverev_method, barrier_method]
    methods_strs = ["minimum_elem_method", "Plotnikov_Zverev_method", "barrier_method"]
    str_methods = (
        Fore.BLUE + "The method of minimal elements | Метод минмальных элементов:" + Style.RESET_ALL,
        Fore.BLUE + "The Plotnikov-Zverev method | Метод Плотникова-Зверева:" + Style.RESET_ALL,
        Fore.BLUE + "The barrier method | Метод барьера:" + Style.RESET_ALL
    )
    if is_create_way != 1:
        way_of_forming_init = {
            0: ("100% random species | 100% рандомных особей", '100r'),
            1: ("50% random + 50% determinate species | 50% рандомно + 50% детерминированных особей", '50r+50d'),
            2: ("25% random + 75% determinate species | 25% рандомно + 75% детерминированных особей", "25r+75d"),
            3: ("75% random + 25% determinate species | 75% рандомно + 25% детерминированных особей", "75r+25d"),
            4: ("50% Plt-Zvr + 50% barrier species | 50% Плт-Зврв + 50% барьерных особей", '50pz+50b')
        }
        way_of_forming_init = {create_way: way_of_forming_init[create_way]}
    else:
        way_of_forming_init = {
            1: ("50% random + 50% determinate species | 50% рандомно + 50% детерминированных особей", '50r+50d'),
            2: ("25% random + 75% determinate species | 25% рандомно + 75% детерминированных особей", "25r+75d"),
            3: ("75% random + 25% determinate species | 75% рандомно + 25% детерминированных особей", "75r+25d"),
            4: ("50% Plt-Zvr + 50% barrier species | 50% Плт-Зврв + 50% барьерных особей", '50pz+50b')
        }

    way_of_forming_genes = {
        0: "central_bound",
        1: "left_bound",
        2: "right_bound",
        3: "random_bound",
        4: "no_bounds"
    }

    repeat_str = Fore.LIGHTYELLOW_EX + str(repeat) + Style.RESET_ALL
    individuals = []
    print(f"Performing a study based on {repeat_str} iterations | Выполняем исследование на основе {repeat_str} итераций")
    # Генерация особей и последующее выполнение ГА
    for way in way_of_forming_init.keys():
        # self._signal_bound.emit(bound_progress)
        # bound_progress += 1
        # method_progress = 1
        work_time, results = [], []
        print(Fore.LIGHTCYAN_EX + way_of_forming_init[way][0] + Style.RESET_ALL)
        # Открываем файл для записи:
        result_file = open(
            f"/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/experiment_results/{way_of_forming_genes[bounds]}/result_{way_of_forming_init[way][1]}.txt",
            'w', encoding="utf-8")
        result_file.write(
            f"Way of forming | Способ формирования:\n{way_of_forming_init[way][0]}\n{way_of_forming_genes[bounds]}\n")
        if way == 4:
            str_methods = ["Plotnikov Zverev and barrier method | Метод Плотникова-Зверева и барьера"]
            methods_strs = ["Plotnikov_Zverev_and_barrier_method"]
            individuals = [generate_individ(methods[1], n, 0, bounds) for _ in range(z // 2)]
            [individuals.append(generate_individ(methods[2], n, 0, bounds)) for _ in range(z // 2)]
            methods = (plotnikov_zverev_method, barrier_method)
            # print(individuals)
        for method, method_str in zip(methods, methods_strs):
            # self._signal_method.emit(str(method_progress) + '/' + str(len(methods_strs)))
            # method_progress += 1
            # Открываем файл для записи:
            if is_create_way != 1:
                if create_way == 0:
                    method_str = "Random formation"
            txt_file = f'/data/data/com.example.lab1/files/chaquopy/AssetFinder/app/methods_data/{method_str}_analysis.txt'
            f = open(txt_file, 'w', encoding="utf-8")
            with tqdm(range(repeat), ncols=100, desc=f"{method_str}") as t:
                for _ in t:
                    if way == 0:
                        [individuals.append(generate_individ(m, n, 1)) for _ in range(z)]
                    elif way == 1:
                        individuals = [generate_individ(method, n, 0, bounds) for _ in range(z//2)]
                        [individuals.append(generate_individ(m, n, 1)) for _ in range(z//2)]
                    elif way == 2:
                        individuals = [generate_individ(method, n, 0, bounds) for _ in range(z//4)]
                        [individuals.append(generate_individ(m, n, 1)) for _ in range((z//4) * 3)]
                    elif way == 3:
                        individuals = [generate_individ(method, n, 0, bounds) for _ in range((z//4) * 3)]
                        [individuals.append(generate_individ(m, n, 1)) for _ in range(z//4)]
                    # Особи нулевого поколения (родители для будущего поколения):
                    listMax = []
                    newline = "\n"
                    for i, individual in enumerate(individuals):
                        load = count_load(individual, n, m, matrix)
                        listMax.append(load)
                    best_result, bestLoad_index = best_load(listMax)  # лучшая загрузка и (индекс лучшей особи - 1)
                    best_individual = individuals[bestLoad_index]
                    previous_best_result, bestLoad_index = 0, 0
                    best_of_all_generations_result = best_result

                    # Переменные для ГА и сам ГА:
                    counter, gen_count = 0, 0

                    while k != counter - 1:
                        previous_best_result = best_result
                        gen_count += 1
                        generation = []
                        best_generation_loads = []
                        for _ in range(z):

                            # Алгоритм образования пар родителей:
                            parent1 = c(individuals)
                            individuals_no_repeat = deepcopy(individuals)
                            individuals_no_repeat.remove(parent1)  # дабы избежать попадание рандома на первого
                            parent2 = c(individuals_no_repeat)
                            while r(0, 100) <= Pk:
                                parent2 = c(individuals_no_repeat)
                            parents_list = (parent1, parent2)

                            # Алгоритм отбора детей из потенциальных особей (2 + 2 мутанта)
                            children = []
                            load_list = []
                            counter_child = 0
                            crossover_result = crossover(parent1, parent2)
                            for i, child in enumerate(crossover_result):
                                children.append(child)
                                load_list.append(count_load(child, n, m, matrix))
                                counter_child += 1
                                muted_child = mutation(child, Pm)
                                children.append(muted_child)
                                load_list.append(count_load(muted_child, n, m, matrix))
                            best_child_load, best_child_index = best_load(load_list)
                            num = 0
                            generation.append(children[best_child_index])

                        # Список всех детей:
                        listMax = []
                        for i, child in enumerate(generation):
                            listMax.append(count_load(child, n, m, matrix))

                        # Индекс лучшего результата в поколении
                        currentLoad = best_load(listMax)[1]

                        # Собираем матрицу родителей и лучших детей для отбора:
                        check_matrix, parent_child_loads = [], []
                        for elem in generation:
                            check_matrix.append(elem)
                            parent_child_loads.append(max(count_load(elem, n, m, matrix)))
                        for elem in individuals:
                            check_matrix.append(elem)
                            parent_child_loads.append(max(count_load(elem, n, m, matrix)))

                        best_result = sorted(parent_child_loads)[0]

                        # Создаём матрицу индексов лучших особей:
                        best_index = []
                        for elem in sorted(parent_child_loads)[:z]:
                            for i, el in enumerate(parent_child_loads):
                                if elem == el:
                                    best_index.append(i)
                                    break

                        # Добавляем лучших особей поколения среди родителей и детей:
                        individuals = []
                        for elem in best_index:
                            individuals.append(check_matrix[elem])


                        # Если сквозь поколения была лучшая загрузка ждем когда она не повторится или улучшится:
                        if best_result < best_of_all_generations_result:
                            best_of_all_generations_result = best_result
                            counter = 0

                        # Если загрузка предыдущего поколения равна загрузке текущего
                        if best_of_all_generations_result == best_result:
                            counter += 1
                        else:
                            counter = 0
                    f.write(f"{best_result} ")
                    # progress += 1
                    # self._signal.emit(progress)
                f.close()
                with open(txt_file, 'r', encoding="utf-8") as f:
                    all_repeats_result = [int(elem) for elem in f.readline().split()]
                    results.append(Fore.YELLOW + str(sum(all_repeats_result) / len(all_repeats_result)) + Style.RESET_ALL)
                work_time.append(Fore.GREEN + str(t.format_interval(t.format_dict['elapsed'])) + Style.RESET_ALL)
                if way == 0:
                    break
                # time.sleep(2)
        # Writing in files
        for iter_method, elapsed_time, result, show_method in zip(str_methods, work_time, results, methods):
            if way == 0:
                iter_method = "Random formation method | Метод рандомного формирования:"
            print(f"\n{iter_method}\nElapsed time | Время работы: {elapsed_time}\nResult | Результат: {result}")
            result_file.write(f"\n{iter_method}\nElapsed time | Время работы: {elapsed_time[5:-4]}\nResult | Результат: {result[5:-4]}\n")
            if way != 4:
                for row in show_method:
                    print(*row)
                    for elem in row:
                        result_file.write(f"{elem} ")
                    result_file.write("\n")
            else:
                for method in methods:
                    for row in method:
                        print(*row)
                        for elem in row:
                            result_file.write(f"{elem} ")
                        result_file.write("\n")
                    result_file.write("\n")
                    print()
                result_file.write("\n")
            result_file.write(f"\n")
        result_file.close()
