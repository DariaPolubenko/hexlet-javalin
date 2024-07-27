@template.layout.page(
content = @`
    <p style="margin-top: 35px" class="fw-light">Добавление курса</p>
    <form action="/courses" method="post">
        <div class="mb-3">
            <label>
                <input type="text" class="form-control" placeholder="Название" name="name"/>
            </label>
        </div>
        <div class="mb-3">
            <label>
                <!-- <input type="text" class="form-control" placeholder="Описание" name="description"/> -->
                <textarea class="form-control" rows="20" name="description"></textarea>
            </label>
        </div>
        <input type="submit" class="btn btn-primary mb-3" value="Добавить"/>
    </form>
`
)